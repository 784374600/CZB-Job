package com.fzubb.service.remote.Utils.Api;

import com.Utils.DataStore.PropertiesUtil;
import com.Utils.DataStore.RedisUtil;
import com.Utils.Net.HttpUtil;
import com.Utils.Tool.FormatUtil;
import com.Utils.Tool.StringUtil;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class QQUtil {
    private  static  String token;
    public  static  String qqlogin(String js_code){
        QQConfig config=new QQConfig(PropertiesUtil.ConfigGet("appId"),PropertiesUtil.ConfigGet("appSecret"),
                js_code,"authorization_code");
        Map<String ,Object> params= FormatUtil.getConfigParams(config);
        return  HttpUtil.get(PropertiesUtil.ConfigGet("QQ.code2Session.address"), null, FormatUtil.FormData(params));
    }
    private  static  class  QQConfig{
        String appid;
        String secret;
        String js_code;
        String grant_type;

        public QQConfig(String appid, String secret, String js_code, String grant_type) {
            this.appid = appid;
            this.secret = secret;
            this.js_code = js_code;
            this.grant_type = grant_type;
        }

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getSecret() {
            return secret;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }

        public String getJs_code() {
            return js_code;
        }

        public void setJs_code(String js_code) {
            this.js_code = js_code;
        }

        public String getGrant_type() {
            return grant_type;
        }

        public void setGrant_type(String grant_type) {
            this.grant_type = grant_type;
        }
    }
    private static  void getAccessToken(){
        QQConfig config=new QQConfig(PropertiesUtil.ConfigGet("appId"),PropertiesUtil.ConfigGet("appSecret"),null,"client_credential");
        Map<String ,Object> params= FormatUtil.getConfigParams(config);
            String res = HttpUtil.get(PropertiesUtil.ConfigGet("QQ.getAccessToken.address"), null, FormatUtil.FormData(params));
            JSONObject jsonObject=JSONObject.fromObject(res);
            long code=jsonObject.getLong("errcode");
            if(code==0){
                  token=jsonObject.getString("access_token");
                  Long time=jsonObject.getLong("expires_in");
                  time=time-100<0?time-10:time-100;
                RedisUtil.putString("QQToken",token,time, TimeUnit.SECONDS);
                System.out.println("获取QQToken成功");
            }

    }
    public  static  JSONObject inform(String openId,String templateId,String formId,JSONObject data){
         if(StringUtil.Empty(token,(String)RedisUtil.getString("QQToken")) ||  !((String)RedisUtil.getString("QQToken")).equals(token))
             getAccessToken();
         Map<String,String> headers=new HashMap<>();
         headers.put("Content-Type", "application/json;charset=UTF-8");
         Map<String,Object> params=new HashMap<>();
         params.put("appid", PropertiesUtil.ConfigGet("appId"));
         params.put("touser", openId);
         params.put("template_id", templateId);
         params.put("form_id", formId);
         params.put("data", data);
         return  JSONObject.fromObject(HttpUtil.post(PropertiesUtil.ConfigGet("QQ.sendTemplateMessage.address")+"?access_token="+token,headers,FormatUtil.JsonFy(params)));
    }
}
