package com.example.exceptions;

public class PlotAlreadyExistException extends Exception{

    public PlotAlreadyExistException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

}