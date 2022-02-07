package com.example.exceptions;

public class AbilityNotFoundException extends Exception{
    public AbilityNotFoundException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
