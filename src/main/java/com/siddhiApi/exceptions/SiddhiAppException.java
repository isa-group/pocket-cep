package com.siddhiApi.exceptions;

public class SiddhiAppException extends Exception{
    public SiddhiAppException(String s) {
        super(s);
    }

    public SiddhiAppException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public SiddhiAppException(Throwable throwable) {
        super(throwable);
    }
}
