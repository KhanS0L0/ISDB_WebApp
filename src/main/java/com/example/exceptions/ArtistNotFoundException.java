package com.example.exceptions;

public class ArtistNotFoundException extends Exception {
    public ArtistNotFoundException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
