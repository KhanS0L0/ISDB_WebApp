package com.example.exceptions.alreadyExistExceptions;

public class PageAlreadyExistException extends Exception{
    public PageAlreadyExistException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
