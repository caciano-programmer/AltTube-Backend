package com.alttube.account.exceptions;

public class EmailNonExistent extends RuntimeException {

    public EmailNonExistent(String email) {
        super("User with email: " + email + " does not exist.");
    }
}
