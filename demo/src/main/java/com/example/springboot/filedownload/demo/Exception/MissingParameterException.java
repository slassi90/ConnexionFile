package com.example.springboot.filedownload.demo.Exception;

public class MissingParameterException extends RuntimeException{
    public MissingParameterException(String message){
        super(message);
    }
}
