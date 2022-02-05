package com.example.exceptions;

public class ScreenwriterNotFoundException extends Exception{
    public ScreenwriterNotFoundException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
