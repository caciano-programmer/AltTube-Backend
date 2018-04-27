package com.alttube.account.services;

import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ExceptionResponseServiceImpl implements ExceptionResponseService {

    private String code;
    private String message;
    private Date timestamp;

    @Override
    public String getCode() { return this.code; }

    @Override
    public void setCode(String code) { this.code = code; }

    @Override
    public Date getDate() { return this.timestamp; }

    @Override
    public void setDate(Date date) { this.timestamp = date; }

    @Override
    public String getMessage() { return this.message; }

    @Override
    public void setMessage(String message) { this.message = message; }
}
