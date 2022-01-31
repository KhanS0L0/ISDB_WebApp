package com.example.exceptions;

public class ProcessNotFoundException extends Exception{
    public ProcessNotFoundException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
