package com.siddhiApi.exceptions;

public class DuplicatedEntity extends Exception{
    public DuplicatedEntity() {
    }

    public DuplicatedEntity(String s) {
        super(s);
    }

    public DuplicatedEntity(String s, Throwable throwable) {
        super(s, throwable);
    }
}
