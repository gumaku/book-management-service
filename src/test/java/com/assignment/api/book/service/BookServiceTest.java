package com.assignment.api.book.service;

import com.assignment.api.book.entity.Book;
import com.assignment.api.book.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Test
    public void testSaveBook() {
        Book book = new Book(1L, "Title", "Author", true);

        when(bookRepository.save(book)).thenReturn(Mono.just(book));

        Mono<Book> mono = bookService.createBook(book);

        StepVerifier
                .create(mono)
                .consumeNextWith(b -> {
                    assertEquals(1L, b.getId());
                    assertEquals("Author", b.getAuthor());
                    assertEquals("Title", b.getTitle());
                    assertTrue(b.isPublished());
                }).verifyComplete();
    }

    @Test
    public void testGetAllBook() {
        Book book1 = new Book(1L, "Title1", "Author1", true);
        Book book2 = new Book(2L, "Title2", "Author2", true);

        when(bookRepository.findAll()).thenReturn(Flux.just(book1, book2));

        Flux<Book> flux = bookService.getAllBooks(null,null);

        StepVerifier
                .create(flux)
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    public void testGetAllBookByAuthor() {
        Book book1 = new Book(1L, "Title1", "Author1", true);
        Book book2 = new Book(2L, "Title2", "Author1", true);

        when(bookRepository.findByAuthor("Author1")).thenReturn(Flux.just(book1,book2));

        Flux<Book> flux = bookService.getAllBooks("Author1",null);

        StepVerifier
                .create(flux)
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    public void testGetAllBookByPublished() {
        Book book1 = new Book(1L, "Title1", "Author1", true);
        Book book2 = new Book(2L, "Title2", "Author1", true);

        when(bookRepository.findByPublished(true)).thenReturn(Flux.just(book1,book2));

        Flux<Book> flux = bookService.getAllBooks(null,true);

        StepVerifier
                .create(flux)
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    public void testGetAllBookByAuthorAndPublished() {
        Book book1 = new Book(1L, "Title1", "Author1", true);
        Book book2 = new Book(2L, "Title2", "Author1", true);

        when(bookRepository.findByAuthorAndPublished("Author1", true)).thenReturn(Flux.just(book1,book2));

        Flux<Book> flux = bookService.getAllBooks("Author1",true);

        StepVerifier
                .create(flux)
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    public void testDeleteBook() {
        when(bookRepository.deleteById(1L)).thenReturn(Mono.empty());
        Mono<Void> mono = bookService.deleteBook(1L);
        StepVerifier
                .create(mono)
                .expectNextCount(0)
                .verifyComplete();
    }


}
