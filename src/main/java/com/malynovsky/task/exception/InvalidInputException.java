package com.malynovsky.task.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Indicates that some parameters are not valid.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidInputException extends RuntimeException {

    public InvalidInputException() {
        super("Invalid parameter");
    }

    public InvalidInputException(String message) {
        super(message);
    }
}
