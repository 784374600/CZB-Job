package com.fzubb.common.config.impl;

import com.fzubb.common.MyComponent.SelectedComponent;
import com.fzubb.common.config.Config;
import lombok.Data;

import java.util.concurrent.atomic.AtomicInteger;
@Data
public class BaiduConfig implements Config , SelectedComponent {

    public  static String get_Token_address;
    public  static  String get_OCRResult_address;
    String client_id;
    String client_secret;
    String grant_type="client_credentials";
    String token;
    int limit=50000;//OCR每天请求上限
    AtomicInteger num=new AtomicInteger(0);


    public BaiduConfig(String client_id, String client_secret) {
        this.client_id = client_id;
        this.client_secret = client_secret;
    }

    @Override
    public boolean selected() {
        if(num.get()>=limit) {
            return false;
        }
        num.addAndGet(1);
        return true;
    }


}
