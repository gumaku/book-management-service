package com.assignment.api.book.repository;

import com.assignment.api.book.entity.Book;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface BookRepository extends ReactiveCrudRepository<Book, Long> {
    Flux<Book> findByAuthor(String author);
    Flux<Book> findByPublished(boolean published);
    Flux<Book> findByAuthorAndPublished(String author, boolean published);
}
