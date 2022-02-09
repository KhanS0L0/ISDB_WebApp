package com.example.exceptions;

public class GenreNotFoundException extends Exception{

    public GenreNotFoundException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
