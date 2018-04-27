package com.alttube.account.controllers;

import com.alttube.account.exceptions.EmailNonExistent;
import com.alttube.account.exceptions.InvalidPassword;
import com.alttube.account.services.ExceptionResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class AccountControllerAdvice {

    private final ExceptionResponseService exceptionResponseService;

    @Autowired
    public AccountControllerAdvice(ExceptionResponseService exceptionResponseService) {
        this.exceptionResponseService = exceptionResponseService;
    }

    @ExceptionHandler({EmailNonExistent.class, InvalidPassword.class})
    public ResponseEntity<ExceptionResponseService> resourceNotFound(RuntimeException ex) {
        this.exceptionResponseService.setCode("Not Found.");
        this.exceptionResponseService.setDate(new Date());
        this.exceptionResponseService.setMessage(ex.getMessage());
        return new ResponseEntity<>(this.exceptionResponseService, HttpStatus.NOT_FOUND);
    }
}
