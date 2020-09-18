package com.siddhiApi.exceptions;

public class StreamOnUseException extends Exception{

    public StreamOnUseException(String s) {
        super(s);
    }

    public StreamOnUseException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public StreamOnUseException(Throwable throwable) {
        super(throwable);
    }
}
