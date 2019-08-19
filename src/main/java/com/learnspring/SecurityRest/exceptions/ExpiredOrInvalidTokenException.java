package com.learnspring.SecurityRest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "Expired or invalid JWT token")
public class ExpiredOrInvalidTokenException extends RuntimeException {

    public ExpiredOrInvalidTokenException(String message) {
        super(message);
    }
}
