package com.example.exceptions.alreadyExistExceptions;

public class TextAlreadyExistException extends Exception{

    public TextAlreadyExistException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
