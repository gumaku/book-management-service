package com.assignment.api.book.controller;

import com.assignment.api.book.entity.Book;
import com.assignment.api.book.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Book> createBook(@Valid @RequestBody Book book) {
        book.setId(null);
        return bookService.createBook(book);
    }

    @GetMapping
    public Flux<Book> getAllBooks(
            @RequestParam(value = "author", required = false) String author,
            @RequestParam(value = "published", required = false) Boolean published) {
        return bookService.getAllBooks(author, published);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteBook(@PathVariable Long id) {
        return bookService.deleteBook(id);
    }
}
