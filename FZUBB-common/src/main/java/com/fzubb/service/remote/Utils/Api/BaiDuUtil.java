package com.fzubb.service.remote.Utils.Api;

import com.MyComponent.SelectedComponent;
import com.MyComponent.Selector;
import com.Utils.DataStore.PropertiesUtil;
import com.Utils.DataStore.RedisUtil;
import com.Utils.Net.HttpUtil;
import com.Utils.Tool.FileUtil;
import com.Utils.Tool.FormatUtil;
import com.Utils.Tool.StringUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class BaiDuUtil {
    static {
           List<BDConfig> configs=new ArrayList<>();
           for(int i=0;;i++){
                 String client_id=PropertiesUtil.ConfigGet("baidu.OCR.client_id("+i+")");
                 String client_secret=PropertiesUtil.ConfigGet("baidu.OCR.client_secret("+i+")");
                 if(StringUtil.Empty(client_id,client_secret))
                     break;
                 configs.add(new BDConfig(client_id, client_secret));
           }
           selector=new Selector((Collection)configs);
    }
    private static Selector selector;
    private static  class BDConfig implements SelectedComponent {

    }
    public  static String[] getOCRResult(MultipartFile file){
          BDConfig config=(BDConfig) selector.next();
          if(config==null)
              return  null;
          return  config.getOCRResult(file);
    }




}