package com.alttube.account.exceptions;

public class InvalidPassword extends RuntimeException {

    public InvalidPassword() {
        super("Invalid Password! Please try again.");
    }
}
