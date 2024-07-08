package com.assignment.api.book.controller;

import com.assignment.api.book.entity.Book;
import com.assignment.api.book.service.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest(BookController.class)
public class BookControllerTest {

    @MockBean
    private BookService bookService;

    @Autowired
    private WebTestClient webClient;

    @Test
    public void testCreateBook() {
        Book book = new Book(null, "Title", "Author", true);
        Book bookReturn = new Book(1L, "Title", "Author", true);

        when(bookService.createBook(book)).thenReturn(Mono.just(bookReturn));

         webClient.post()
                .uri("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(book)
                .exchange()
                .expectStatus().isCreated()
                 .expectBody(Book.class).isEqualTo(bookReturn);
    }

    @Test
    public void testCreateBookWithNoRequestBody() {

        webClient.post()
                .uri("/api/books")
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    public void testCreateBookWithInvalidAuthor() {
        Book book = new Book(1L, "Title", "Author***", true);

        when(bookService.createBook(book)).thenReturn(Mono.just(book));

        webClient.post()
                .uri("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(book)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    public void testCreateBookWithInvalidTitle() {
        Book book = new Book(1L, "T i t l e", "Author", true);

        when(bookService.createBook(book)).thenReturn(Mono.just(book));

        webClient.post()
                .uri("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(book)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    public void testGetAllBooks() {
        Book book1 = new Book(1L, "Title1", "Author1", true);
        Book book2 = new Book(2L, "Title2", "Author2", true);

        when(bookService.getAllBooks(null, null)).thenReturn(Flux.just(book1, book2));

        webClient.get()
                .uri("/api/books")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Book.class)
                .contains(book1, book2);
    }

    @Test
    public void testGetBooksByAuthor() {
        Book book = new Book(1L, "Title1", "Author1", true);

        when(bookService.getAllBooks("Author1", null)).thenReturn(Flux.just(book));

        webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/books")
                        .queryParam("author", "Author1")
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Book.class)
                .contains(book);
    }

    @Test
    public void testGetBooksByPublished() {
        Book book = new Book(1L, "Title1", "Author1", true);

        when(bookService.getAllBooks(null, true)).thenReturn(Flux.just(book));

        webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/books")
                        .queryParam("published", true)
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Book.class)
                .contains(book);
    }

    @Test
    public void testGetBooksByAuthorAndPublished() {
        Book book = new Book(1L, "Title1", "Author1", true);


        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("author", "Author1");
        params.add("published", "true");

        when(bookService.getAllBooks("Author1", true)).thenReturn(Flux.just(book));

        webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/books")
                        .queryParams(params)
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Book.class)
                .contains(book);
    }

    @Test
    public void testDeleteBook() {
        when(bookService.deleteBook(anyLong())).thenReturn(Mono.empty());

        webClient.delete()
                .uri("/api/books/1")
                .exchange()
                .expectStatus().isNoContent();
    }
}
