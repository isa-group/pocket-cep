package com.siddhiApi.exceptions;

public class PropertyNotFoundOnSelect extends Exception{

    public PropertyNotFoundOnSelect() {
    }

    public PropertyNotFoundOnSelect(String s) {
        super(s);
    }

    public PropertyNotFoundOnSelect(String s, Throwable throwable) {
        super(s, throwable);
    }
}
