package com.fzubb.service.remote.Utils.DataStore;

import com.Application;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class PropertiesUtil {
       private  static  Properties configProperties;
       private  static  Properties errorCodeProperties;
       static {
           configProperties=init("Config.properties");
           errorCodeProperties=init("ErrorCode.properties");

       }
    private  static  Properties init(String relaPath){
        Properties properties = new Properties();
        InputStreamReader in=null;
        // 使用ClassLoader加载properties配置文件生成对应的输入流
        try {
           in=new InputStreamReader(Application.class.getResourceAsStream("/"+relaPath), "UTF-8");
            // 使用properties对象加载输入流
            properties.load(in);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(in!=null)
                    in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return  properties;
    }
    public  static  String ConfigGet(String key){
           return  (String)configProperties.get(key);
    }
    public  static  String ErrorCodeGet(int key){
        return  (String)errorCodeProperties.get(String.valueOf(key));
    }
}
