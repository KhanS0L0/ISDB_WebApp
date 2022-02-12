package com.example.exceptions.notFoundExceptions;

public class CharacterNotFoundException extends Exception{

    public CharacterNotFoundException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
