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
