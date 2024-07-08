package com.assignment.api.book.entity;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("books")
public class Book {
    @Id
    private Long id;
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Title must be alphanumeric")
    private String title;
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Author must be alphanumeric")
    private String author;

    private boolean published;
}
