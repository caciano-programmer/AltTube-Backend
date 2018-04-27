package com.alttube.account.services;

import java.util.Date;

public interface ExceptionResponseService {

    String getCode();

    void setCode(String code);

    Date getDate();

    void setDate(Date date);

    String getMessage();

    void setMessage(String message);
}
