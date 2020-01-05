package com.fzubb.service.remote.impl;

import com.alibaba.fastjson.JSONObject;
import com.fzubb.ExcepTion.QQServiceException;
import com.fzubb.config.impl.QQConfig;
import com.fzubb.constant.CacheKey;
import com.fzubb.service.remote.QQService;
import com.fzubb.service.remote.Utils.Api.QQUtil;
import com.fzubb.util.FormatUtil;
import com.fzubb.util.HttpUtil;
import com.fzubb.util.RedisUtil;
import org.apache.dubbo.common.logger.Logger;
import org.apache.dubbo.common.logger.LoggerFactory;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class QQServiceImpl implements QQService{
     private  static Logger logger= LoggerFactory.getLogger(QQServiceImpl.class);
     @Autowired
     RedisTemplate<String,Object> cacheClient;
    private    String getAccessToken(CloseableHttpClient httpClient, QQConfig config){
        //此处应对进行配置，待实现
        //QQUtil.QQConfig config=new QQUtil.QQConfig(PropertiesUtil.ConfigGet("appId"),PropertiesUtil.ConfigGet("appSecret"),null,"client_credential");

        String res = null;
        try {
            res = HttpUtil.get(httpClient,QQConfig.QQ_getAccessToken_address, null, FormatUtil.formData(config.params()));
        } catch (Exception e) {
            logger.warn(config.getAppid()+"获取qqtoken出错");
            throw  new QQServiceException("获取qqtoken出错");
        }
        JSONObject jsonObject= JSONObject.parseObject(res);
        long code=jsonObject.getLong("errcode");
        String token;
        if(code==0){
            token=jsonObject.getString("access_token");
            Long time=jsonObject.getLong("expires_in");
            time=time-100<0?time-10:time-100;
            RedisUtil.put(cacheClient,CacheKey.QQ_Token.getKeyWithParams(),token,time, TimeUnit.SECONDS);
            logger.info("获取到qqToken:"+token);
        }else {
             logger.warn("获取qq Token 异常："+res);
             throw  new QQServiceException("获取qqtoken出错");
        }
        return  token;
    }
}
