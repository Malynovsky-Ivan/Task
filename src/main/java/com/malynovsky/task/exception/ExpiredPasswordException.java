package com.malynovsky.task.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Indicates that OTP is not valid anymore.
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class ExpiredPasswordException extends RuntimeException {

    public ExpiredPasswordException() {
        super("Your token has expired.");
    }
}
