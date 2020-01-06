package com.fzubb.common.ExcepTion;

public class WrongParamException  extends RuntimeException{
    public WrongParamException() {
        super();
    }

    public WrongParamException(String message) {
        super(message);
    }
}
