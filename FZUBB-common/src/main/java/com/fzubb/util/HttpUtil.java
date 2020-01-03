package com.fzubb.util;

import com.fzubb.ExcepTion.HttpException;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpUriRequestBase;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.util.Timeout;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

public class HttpUtil {

    private   static  String  request(CloseableHttpClient httpClient,String url, String method, Map<String,String> headers, String content, Charset charset)throws HttpException {
        HttpUriRequestBase request;
        if(method.equals("GET")) {
            if (content != null && !content.equals(""))
                url=url+"?"+ content;
            request = new HttpGet(url);
        }
        else {
            request = new HttpPost(url);
            if (content != null && !content.equals("")) {
                StringEntity entity = new StringEntity(content,Charset.forName("utf-8"));
                request.setEntity(entity);
            }
        }
        //构建超时等配置信息
        RequestConfig config = RequestConfig.custom().setConnectTimeout(Timeout.ofSeconds(3)) //连接超时时间
                .setConnectionRequestTimeout(Timeout.ofSeconds(5))//从连接池中取的连接的最长时间
                .setResponseTimeout(Timeout.ofSeconds(10))
                .build();
        //设置请求配置时间
        request.setConfig(config);
        String resContentontent=null;
        if(headers!=null)
            for (Map.Entry<String,String> entry:headers.entrySet()
            ) {
                request.addHeader(entry.getKey(), entry.getValue());
            }
        CloseableHttpResponse response=null;
        try {
            response = httpClient.execute(request);
            if (charset==null)
                charset=Charset.forName("utf-8");
            resContentontent= EntityUtils.toString(response.getEntity(),charset);
        } catch (Exception e) {
            throw new HttpException("url:"+url+" request error");
        }finally {
            try {
                if(response!=null)
                    response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return   resContentontent;
    }
    public  static  String get(CloseableHttpClient httpClient, String url,Map<String,String> headers, String content){
        return  request(httpClient,url, "GET",headers,content,null);
    }
    public  static  String get(CloseableHttpClient httpClient,String url, Map<String,String> headers, String content, Charset charset ){
        return  request(httpClient,url, "GET",headers,content,charset);
    }
    public  static   String  post(CloseableHttpClient httpClient, String url,Map<String,String> headers, String content){
        return  request(httpClient,url, "POST",headers,content,null);
    }
    public  static   String  post( CloseableHttpClient httpClient,String url,Map<String,String> headers, String content,Charset charset){
        return  request(httpClient,url, "POST",headers,content,charset);
    }


    public   static CloseableHttpClient getHttpClient(){
       return  HttpClients.custom().build();

    }

}
