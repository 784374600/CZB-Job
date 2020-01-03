package com.fzubb.config.impl;

import com.alibaba.fastjson.JSONObject;
import com.fzubb.ExcepTion.BaiduServiceException;
import com.fzubb.ExcepTion.HttpException;
import com.fzubb.MyComponent.SelectedComponent;
import com.fzubb.config.Config;
import com.fzubb.constant.CacheKey;
import com.fzubb.util.*;
import lombok.Data;
import org.apache.dubbo.common.logger.Logger;
import org.apache.dubbo.common.logger.LoggerFactory;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
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
        if(num.get()>=limit)
            return  false;
        num.addAndGet(1);
        return true;
    }


}
