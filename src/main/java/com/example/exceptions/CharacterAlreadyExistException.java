package com.example.exceptions;

public class CharacterAlreadyExistException extends Exception{

    public CharacterAlreadyExistException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
