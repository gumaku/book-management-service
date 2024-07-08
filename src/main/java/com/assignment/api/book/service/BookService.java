package com.assignment.api.book.service;

import com.assignment.api.book.entity.Book;
import com.assignment.api.book.repository.BookRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Mono<Book> createBook(Book book) {
        return bookRepository.save(book);
    }

    public Flux<Book> getAllBooks(String author, Boolean published) {
        if (author != null && published != null) {
            return bookRepository.findByAuthorAndPublished(author, published);
        } else if (author != null) {
            return bookRepository.findByAuthor(author);
        } else if (published != null) {
            return bookRepository.findByPublished(published);
        } else {
            return bookRepository.findAll();
        }
    }

    public Mono<Void> deleteBook(Long id) {
        return bookRepository.deleteById(id);
    }
}
