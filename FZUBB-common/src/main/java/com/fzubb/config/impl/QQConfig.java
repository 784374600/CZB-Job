package com.fzubb.config.impl;

import com.fzubb.config.Config;
import lombok.Data;

@Data
public class QQConfig implements Config {
    String appid;
    String secret;
    String js_code;
    String grant_type;
    public  static  String QQ_code2Session_address;
    public  static  String QQ_getAccessToken_address;

    public QQConfig(String appid, String secret, String js_code, String grant_type) {
        this.appid = appid;
        this.secret = secret;
        this.js_code = js_code;
        this.grant_type = grant_type;
    }
}
