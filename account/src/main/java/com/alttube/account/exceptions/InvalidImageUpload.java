package com.alttube.account.exceptions;

public class InvalidImageUpload extends RuntimeException {

    public InvalidImageUpload() { super("Upload was not an image or the file size is too large"); }
}
