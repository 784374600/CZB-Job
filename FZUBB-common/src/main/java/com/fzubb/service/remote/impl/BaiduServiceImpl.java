package com.fzubb.service.remote.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fzubb.ExcepTion.BaiduServiceException;
import com.fzubb.ExcepTion.HttpException;
import com.fzubb.ExcepTion.ServerNotServeException;
import com.fzubb.MyComponent.Selector;
import com.fzubb.config.impl.BaiduConfig;
import com.fzubb.constant.CacheKey;
import com.fzubb.constant.MQConstant;
import com.fzubb.service.remote.BaiduService;
import com.fzubb.service.remote.PhotoService;
import com.fzubb.util.*;
import org.apache.dubbo.common.logger.Logger;
import org.apache.dubbo.common.logger.LoggerFactory;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
@Service
public class BaiduServiceImpl implements BaiduService {
    private  static Logger logger= LoggerFactory.getLogger(BaiduService.class);
    private  static  Selector<BaiduConfig> selector;

    @Autowired
    RedisTemplate<String,Object> cacheClient;

    @Autowired
    DefaultMQProducer producer;


    @Override
    public String[] getOCResult(String photoUrl) {
        /*使用不同账户调用百度api  因为每日超出次数要花钱*/
        BaiduConfig config=selector.next();
        if(Objects.isNull(config)){
            logger.error("百度ocr api调用达今日上限");
            throw  new ServerNotServeException("百度ocr api调用达今日上限");
        }

        /*获取百度OCR token*/
        CloseableHttpClient httpClient= HttpUtil.getHttpClient();
        String token=RedisUtil.get(cacheClient,CacheKey.Baidu_Token.getKeyWithParams(config.getClient_id()));
        token= StringUtil.isEmpty(token)?getAccessToken(httpClient,config):token;


        String[] result;
        try {
            result = null;
            Map<String,String> headers=new HashMap<>();
            headers.put("Content-Type","application/x-www-form-urlencoded");
            String detect_direction="true";
            Map<String,Object>  params=new HashMap<>();
            params.put("url",photoUrl);
            params.put("detect_direction",detect_direction);
            String res=HttpUtil.post(httpClient,BaiduConfig.get_OCRResult_address+"?access_token="+token, headers,FormatUtil.formData(params));
            if(res!=null) {
                JSONObject jsonObject = JSONObject.parseObject(res);
                JSONArray jsonArray = jsonObject.getJSONArray("words_result");
                if(jsonArray!=null && jsonArray.size()!=0) {
                    result = new String[jsonArray.size()];
                    for (int i = 0; i < jsonArray.size(); i++) {
                        result[i] = JSONObject.parseObject(jsonArray.get(i).toString()).getString("words");
                    }
                }
            }
        }
        catch (HttpException e){
             logger.warn(config.getClient_id()+"请求获取百度OCR服务出错");
             throw  new BaiduServiceException(config.getClient_id()+"请求获取百度OCR服务出错") ;
        } finally {
            try {
                if(httpClient!=null)
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }


            /*发送删除图片消息,*/
            try {
                producer.send(new Message(MQConstant.FZUBB_Topic, MQConstant.del_Img_Tag,photoUrl.getBytes()));
            } catch (Exception e) {
                logger.warn(MessageFormat.format("发送删除图片消息出错 图片地址：{0}", photoUrl));
            }
        }
        return  result;
    }


    /**获取百度OCR token*/
    private    String getAccessToken(CloseableHttpClient httpClient,BaiduConfig config){
        String res= null;
        try {
            res = HttpUtil.post(httpClient,BaiduConfig.get_Token_address+"?"+ FormatUtil.formData(config.params()),null,null);
        } catch (HttpException e) {
            logger.warn("获取百度token出错");
            throw new BaiduServiceException("获取百度token出错");
        }
        JSONObject object=JSONObject.parseObject(res, JSONObject.class);
        String token=object.getString("access_token");
        config.setToken(token);
        long time=object.getLong("expires_in");
        RedisUtil.put(cacheClient,CacheKey.Baidu_Token.getKeyWithParams(config.getClient_id()), token, time, TimeUnit.SECONDS);
        logger.info(config.getClient_id()+"获取到百度token:"+token);
        return  token;
    }
}
