package com.example.sideproject.exception;

public class CustomNoSuchElementException extends BaseException{
    public CustomNoSuchElementException(int code, String message) {
        super(code, message);
    }
}
