package com.siddhiApi.exceptions;

public class CyclePatternError extends Exception{

    public CyclePatternError(String s) {
        super(s);
    }

    public CyclePatternError(String s, Throwable throwable) {
        super(s, throwable);
    }

    public CyclePatternError(Throwable throwable) {
        super(throwable);
    }

}
