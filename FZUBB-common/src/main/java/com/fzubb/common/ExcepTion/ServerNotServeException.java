package com.fzubb.common.ExcepTion;
/**服务器不服务异常 有以下可能 1.百度api调用达到上限 2.服务器出错*/
public class ServerNotServeException  extends  RuntimeException{
    public ServerNotServeException() {
        super();
    }

    public ServerNotServeException(String message) {
        super(message);
    }
}
