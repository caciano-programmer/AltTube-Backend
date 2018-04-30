package com.alttube.account.services;

public interface ExceptionService {

    void throwEmailNonExistentException(String email);

    void throwInvalidPasswordException();

    void throwInvalidCredentialsFormatException();

    void throwInvalidImage();
}
