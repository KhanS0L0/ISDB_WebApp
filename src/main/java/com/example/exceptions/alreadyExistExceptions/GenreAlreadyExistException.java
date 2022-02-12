package com.example.exceptions.alreadyExistExceptions;

public class GenreAlreadyExistException extends Exception{

    public GenreAlreadyExistException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
