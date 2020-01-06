package com.fzubb.common.ExcepTion;
/**教务处系统异常*/
public class SchoolSystemException extends  RuntimeException {
    public SchoolSystemException(String message) {
        super(message);
    }
}
