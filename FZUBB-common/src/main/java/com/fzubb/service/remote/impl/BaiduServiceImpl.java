package com.fzubb.service.remote.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fzubb.ExcepTion.BaiduServiceException;
import com.fzubb.ExcepTion.HttpException;
import com.fzubb.ExcepTion.ServerNotServeException;
import com.fzubb.Model.Photo;
import com.fzubb.MyComponent.Selector;
import com.fzubb.config.impl.BaiduConfig;
import com.fzubb.constant.CacheKey;
import com.fzubb.service.remote.BaiduService;
import com.fzubb.service.remote.PhotoService;
import com.fzubb.util.*;
import org.apache.dubbo.common.logger.Logger;
import org.apache.dubbo.common.logger.LoggerFactory;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class BaiduServiceImpl implements BaiduService {
    private  static Logger logger= LoggerFactory.getLogger(BaiduService.class);
    private  static  Selector<BaiduConfig> selector;
    @Autowired
    PhotoService photoService;
    @Override
    public String[] getOCResult(MultipartFile file) {
        BaiduConfig config=selector.next();
        if(Objects.isNull(config)){
            logger.error("百度ocr api调用达今日上限");
            throw  new ServerNotServeException("百度ocr api调用达今日上限");
        }

        CloseableHttpClient httpClient= HttpUtil.getHttpClient();
        String token=RedisUtil.get(CacheKey.Baidu_Token.getKeyWithParams(config.getClient_id()));
        token= StringUtil.isEmpty(token)?getAccessToken(httpClient,config):token;
        String[] result;
        String filePath= null;
        try {
            result = null;
            Map<String,String> headers=new HashMap<>();
            headers.put("Content-Type","application/x-www-form-urlencoded");
            String detect_direction="true";
            Photo photo =  photoService.upload(file);
            if(photo==null)
                throw  new IOException();
            Map<String,Object>  params=new HashMap<>();
            params.put("url", photo.url());
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
        } catch (IOException e) {
            logger.warn("获取百度OCR服务过程保存图片出错："+e.getMessage());
            throw  new BaiduServiceException("获取百度OCR服务过程保存图片出错："+e.getMessage()) ;
        }finally {
            try {
                if(httpClient!=null)
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            /**此处应实现发送删除图片消息,待实现*/
            if(filePath!=null)
                FileUtil.delFile(new File(filePath));
        }
        return  result;
    }
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
        RedisUtil.putString(CacheKey.Baidu_Token.getKeyWithParams(config.getClient_id()), token, time, TimeUnit.SECONDS);
        logger.info(config.getClient_id()+"获取到百度token:"+token);
        return  token;
    }
}
