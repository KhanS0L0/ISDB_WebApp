package com.example.exceptions.notFoundExceptions;

public class TextNotFoundException extends Exception {

    public TextNotFoundException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
