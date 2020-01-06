package com.fzubb.common.ExcepTion;
/**百度接口异常*/
public class BaiduServiceException extends  RuntimeException {
    public BaiduServiceException(String message) {
        super(message);
    }
}
