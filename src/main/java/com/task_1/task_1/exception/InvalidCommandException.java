package com.task_1.task_1.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// This will cause Spring to respond with a 400 status
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidCommandException extends RuntimeException {
    public InvalidCommandException(String message) {
        super(message);
    }
}