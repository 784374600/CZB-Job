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
        String client_id;
        String client_secret;
        String grant_type="client_credentials";
        String token;
        int limit=50000;//OCR每天请求上限
        AtomicInteger num=new AtomicInteger(0);

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public BDConfig(String client_id, String client_secret) {
            this.client_id = client_id;
            this.client_secret = client_secret;
        }

        @Override
        public boolean selected() {
            if(num.get()>=limit)
                return  false;
            return true;
        }

        private    void getAccessToken(){
            String res= HttpUtil.post(PropertiesUtil.ConfigGet("baidu.access_token.address")+"?"+ FormatUtil.getFormStringByConfig(this), null,null);
            JSONObject object=JSONObject.parseObject(res);
            this.token= object.getString("access_token");
            long time=object.getLong("expires_in");
            RedisUtil.putString("BDOCRToken"+"_"+this.client_id, this.token, time, TimeUnit.SECONDS);
            System.out.println("重新获取百度Token");
        }
        public  String[]  getOCRResult(MultipartFile file){
            String redisToken=(String)RedisUtil.getString("BDOCRToken"+"_"+this.client_id);
            if(StringUtil.Empty(redisToken,this.token) || !this.token.equals(redisToken))
                getAccessToken();
            String[] result= new String[0];
            String filePath= null;
            try {
                result = null;
                Map<String,String> headers=new HashMap<>();
                headers.put("Content-Type","application/x-www-form-urlencoded");
                String detect_direction="true";
                String photoPath= null;
                filePath = "/images/"+ FileUtil.saveImg(file,"/images");
                photoPath =  PropertiesUtil.ConfigGet("photo.pre")+filePath;
                Map<String,Object>  params=new HashMap<>();
                System.out.println("url:"+photoPath);
                params.put("url", photoPath);
                params.put("detect_direction",detect_direction);
                String res=HttpUtil.post(PropertiesUtil.ConfigGet("baidu.OCR.address")+"?access_token="+this.getToken(), headers,FormatUtil.FormData(params));
                if(res!=null) {
                    JSONObject jsonObject = JSONObject.parseObject(res);
                    System.out.println("百度识别结果："+res);
                    JSONArray jsonArray = jsonObject.getJSONArray("words_result");
                    if(jsonArray!=null) {
                        result = new String[jsonArray.size()];
                        for (int i = 0; i < jsonArray.size(); i++) {
                            result[i] = JSONObject.parseObject(jsonArray.get(i).toString()).getString("words");
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(filePath!=null)
                    FileUtil.delFile(new File(filePath));
            }
            this.num.getAndAdd(1);
            return  result;
        }
    }
    public  static String[] getOCRResult(MultipartFile file){
          BDConfig config=(BDConfig) selector.next();
          if(config==null)
              return  null;
          return  config.getOCRResult(file);
    }




}