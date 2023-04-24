package com.example.springboot.filedownload.demo.Exception;

public class ApiRequestException extends RuntimeException {
    public ApiRequestException (String message){
        super(message);
    }
    public ApiRequestException(String message,Throwable cause){
        super(message,cause);
    }

}
