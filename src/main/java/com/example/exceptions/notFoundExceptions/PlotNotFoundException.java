package com.example.exceptions.notFoundExceptions;

public class PlotNotFoundException extends Exception{

    public PlotNotFoundException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
