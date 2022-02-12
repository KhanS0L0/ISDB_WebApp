package com.example.exceptions.notFoundExceptions;

public class PageNotFoundException extends Exception{
    public PageNotFoundException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
