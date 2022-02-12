package com.example.exceptions.alreadyExistExceptions;

public class UserAlreadyExistsException extends Exception{
    public UserAlreadyExistsException(String msg){
        super(msg);
    }

    @Override
    public String getMessage(){
        return super.getMessage();
    }

}