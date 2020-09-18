package com.siddhiApi.exceptions;

public class NotFoundException extends Exception{

    public NotFoundException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public NotFoundException(String s) {
        super(s);
    }
}
