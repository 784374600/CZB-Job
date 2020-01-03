package com.fzubb.ExcepTion;
/**密码错误异常*/
public class WrongPswException extends  RuntimeException{
    public WrongPswException() {
        super("密码错误");
    }
}
