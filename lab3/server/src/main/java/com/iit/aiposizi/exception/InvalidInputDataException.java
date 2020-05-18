package com.iit.aiposizi.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class InvalidInputDataException extends RuntimeException {

    @Getter
    private final HttpStatus status;

    public InvalidInputDataException(final String message) {
        super(message);
        this.status = BAD_REQUEST;
    }

}
