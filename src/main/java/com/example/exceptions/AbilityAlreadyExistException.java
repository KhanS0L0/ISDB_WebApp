package com.example.exceptions;

public class AbilityAlreadyExistException extends Exception{
    public AbilityAlreadyExistException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
