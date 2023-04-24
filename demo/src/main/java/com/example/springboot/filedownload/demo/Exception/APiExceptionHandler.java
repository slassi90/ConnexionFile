package com.example.springboot.filedownload.demo.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;
@ControllerAdvice
public class APiExceptionHandler {
    @ExceptionHandler(value = {ApiRequestException.class})
    public ResponseEntity<Object> HandleApiRequestException(ApiRequestException e){
        //Create payload containing exception details
       ApiException apiException = new ApiException(
                e.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now(ZoneId.of("Z"))       );
        //return response Entity
        return new ResponseEntity<>(apiException,HttpStatus.BAD_REQUEST);
    }

}
