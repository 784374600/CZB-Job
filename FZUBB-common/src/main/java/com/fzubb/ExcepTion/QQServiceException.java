package com.fzubb.ExcepTion;
/**qq接口异常*/
public class QQServiceException extends RuntimeException {
    public QQServiceException(String message) {
        super(message);
    }
}
