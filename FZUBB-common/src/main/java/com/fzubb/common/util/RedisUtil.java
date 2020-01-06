package com.fzubb.common.util;


import org.apache.dubbo.common.logger.Logger;
import org.apache.dubbo.common.logger.LoggerFactory;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.*;

import java.text.MessageFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;


public class RedisUtil {
    private  static Logger logger= LoggerFactory.getLogger(RedisUtil.class);
    /**删除键*/
    public  static  void del(RedisTemplate<String,Object> client,String key){
        if(client==null)
            return;
        try {
            if(!client.delete(key))
                logger.warn(MessageFormat.format("删除键失败 key:{0}", key));
        } catch (NullPointerException e) {
            logger.info(MessageFormat.format("删除不存在的键 key:{0}", key));
        }
    }
    public  static  boolean exist(RedisTemplate<String,Object> client,String key){
        return  client.hasKey(key);
    }

    /**设置存活时间*/
    public  static  void expire(RedisTemplate<String,Object> client,String key,long time,TimeUnit unit){
        if(!StringUtil.isEmpty(key) && time>0 && unit!=null &&client.hasKey(key)){
            client.expire(key, time, unit);
        }
    }
    /**设置定时过期*/
    public  static  void expireAt(RedisTemplate<String,Object> client, String key, Date date){
        if(!StringUtil.isEmpty(key) &&date!=null &&client.hasKey(key)){
            client.expireAt(key,date);
        }
    }

    /**字符key操作*/
    public  static  void put(RedisTemplate<String,Object> client,String key,Object value){
        client.opsForValue().set(key, value);
    }
    public  static  void put(RedisTemplate<String,Object> client, String key, Object value, Long time, TimeUnit timeUnit){
        client.opsForValue().set(key, value,time,timeUnit);
    }
    public  static  <T>  T get(RedisTemplate<String,Object> client,String key){
            return  (T)client.opsForValue().get(key);
    }


    /**集合key操作*/
    public  static  void sput(RedisTemplate<String,Object> client,String key,Object... value){
        client.opsForSet().add(key,value);
    }

    /**hash key操作*/
    public static  void hput(RedisTemplate<String,Object> client,String key,Object hkey,Object value){
        client.opsForHash().put(key, hkey, value);
    }
    public static  <T>  T hgetEntrys(RedisTemplate<String,Object> client,String key){
        return  (T)client.opsForHash().entries(key);
    }
    public static  void hputEntrys(RedisTemplate<String,Object> client,String key, Map<Object,Object> entries){
          client.opsForHash().putAll(key,entries);
    }
    public static  <T>  T hget(RedisTemplate<String,Object> client,String key,Object hkey){
         return  (T)client.opsForHash().get(key, hkey);
    }
    public  static  void hdel(RedisTemplate<String,Object> client,String key,Object hkey){
        client.opsForHash().delete(key, hkey);
    }
    public  static  long  hinc(RedisTemplate<String,Object> client,String key,Object hkey,long inc){
         return client.opsForHash().increment(key,hkey,inc);

    }
    /**zset key操作*/
    public static  void zput(RedisTemplate<String,Object> client,String key,Object value,double score
                             ){
        client.opsForZSet().add(key, value,score);
    }
    /**zset key操作 从小到大*/
    public static  <T>  Set<T> zget(RedisTemplate<String,Object> client, String key, long start, long end
    ){
        return (Set<T>)client.opsForZSet().range(key, start, end);
    }
    /**zset key操作 从大到小*/
    public static  <T>  Set<T>  zgetrev(RedisTemplate<String,Object> client,String key,long start,long end
    ){
        return (Set<T>)client.opsForZSet().reverseRange(key, start, end);
    }
    /**zset key操作  比较分数,注意包括max边界值，在offset为0时*/
    public static  <T>  Set<T> zgetrevByScore(RedisTemplate<String,Object> client,String key,double min,double max,int offset,int count
    ){
        return  (Set<T>)client.opsForZSet().reverseRangeByScore(key, min, max, offset, count);
    }
    private  static  <T>  Set<T> zgetByLex(RedisTemplate<String,Object> client, String key, RedisZSetCommands.Range range, RedisZSetCommands.Limit limit){
        return (Set<T>)client.opsForZSet().rangeByLex(key, range, limit);
    }



}
