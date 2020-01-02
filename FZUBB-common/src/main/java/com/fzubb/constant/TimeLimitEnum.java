package com.fzubb.constant;

public enum TimeLimitEnum {
    Recent_Week(7,"最近一周"),Today(1,"今天"),Recent_Three_Day(3,"最近3天");
    int  code;
    String desc;
    TimeLimitEnum(int code,String desc){
        this.desc=desc;
    }
}
