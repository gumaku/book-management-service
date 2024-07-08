package com.assignment.api.book.exception;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class ErrorResponse {
    private List<String> message;
}
