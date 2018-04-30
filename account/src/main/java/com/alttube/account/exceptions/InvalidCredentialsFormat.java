package com.alttube.account.exceptions;

public class InvalidCredentialsFormat extends RuntimeException {

    public InvalidCredentialsFormat() { super("Login credentials are in improper form, please try to login again."); }
}
