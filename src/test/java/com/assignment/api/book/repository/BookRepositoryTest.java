package com.assignment.api.book.repository;

import com.assignment.api.book.entity.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertNotNull;


@ExtendWith(SpringExtension.class)
@DataR2dbcTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void testPostRepositoryExisted() {
        assertNotNull(bookRepository);
    }

    @Test
    public void testSaveBook() {
        Book book = Book.builder()
                .title("Title1")
                .author("Author1")
                .published(true).build();
        Flux<Book> setup = bookRepository.deleteAll().thenMany(bookRepository.save(book));
        StepVerifier
                .create(setup)
                .expectNextMatches(b -> b.getAuthor().equals("Author1"))
                .verifyComplete();
    }

    @Test
    public void testQueryBookByAuthor() {
        Book book1 = Book.builder()
                .title("Title1")
                .author("Author1")
                .published(true).build();
        Book book2 = Book.builder()
                .title("Title2")
                .author("Author2")
                .published(false).build();

        Flux<Book> setup = bookRepository.deleteAll()
                .thenMany(bookRepository.save(book1))
                .thenMany(bookRepository.save(book2))
                .thenMany(bookRepository.findByAuthor("Author1"));

        StepVerifier
                .create(setup)
                .expectNextMatches(b -> b.getAuthor().equals("Author1"))
                .verifyComplete();
    }

    @Test
    public void testQueryBookByPublished() {
        Book book1 = Book.builder()
                .title("Title1")
                .author("Author1")
                .published(true).build();
        Book book2 = Book.builder()
                .title("Title2")
                .author("Author2")
                .published(false).build();

        Flux<Book> setup = bookRepository.deleteAll()
                .thenMany(bookRepository.save(book1))
                .thenMany(bookRepository.save(book2))
                .thenMany(bookRepository.findByPublished(false));

        StepVerifier
                .create(setup)
                .expectNextMatches(b -> b.getAuthor().equals("Author2"))
                .verifyComplete();
    }

    @Test
    public void testQueryAll() {
        Book book1 = Book.builder()
                .title("Title1")
                .author("Author1")
                .published(true).build();
        Book book2 = Book.builder()
                .title("Title2")
                .author("Author2")
                .published(false).build();

        Flux<Book> setup = bookRepository.deleteAll()
                .thenMany(bookRepository.save(book1))
                .thenMany(bookRepository.save(book2))
                .thenMany(bookRepository.findAll());

        StepVerifier
                .create(setup)
                .expectNextCount(2)
                .verifyComplete();
    }

}
