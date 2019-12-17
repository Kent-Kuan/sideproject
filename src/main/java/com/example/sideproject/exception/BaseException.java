package com.example.sideproject.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=true)
@Data
public class BaseException extends Exception {
    private int status;
    private String message;

    public BaseException(int status, String message) {
        this.status = status;
        this.message = message;
    }
}