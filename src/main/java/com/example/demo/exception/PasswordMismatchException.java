package com.example.demo.exception;

public class PasswordMismatchException extends Exception{

    public PasswordMismatchException(String message){
        super(message);
    }
}
