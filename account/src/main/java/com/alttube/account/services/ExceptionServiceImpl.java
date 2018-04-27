package com.alttube.account.services;

import com.alttube.account.exceptions.EmailNonExistent;
import com.alttube.account.exceptions.InvalidPassword;
import org.springframework.stereotype.Service;

@Service
public class ExceptionServiceImpl implements ExceptionService {

    @Override
    public void throwEmailNonExistentException(String email) { throw new EmailNonExistent(email); }

    @Override
    public void throwInvalidPasswordException() { throw new InvalidPassword(); }
}
