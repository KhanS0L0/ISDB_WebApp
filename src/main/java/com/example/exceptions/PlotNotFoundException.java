package com.example.exceptions;

public class PlotNotFoundException extends Exception{

    public PlotNotFoundException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
