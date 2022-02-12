package com.example.exceptions.notFoundExceptions;

public class GenreNotFoundException extends Exception{

    public GenreNotFoundException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
