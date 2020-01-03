package com.fzubb.util;


import com.fzubb.service.remote.Utils.DataStore.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {
    private   static  RedisUtil instance;
    @PostConstruct
      private void init() {
              instance = this;
           }
    @Autowired
     private  HashOperations<String, String, Object> hashOp;
    @Autowired
    private ValueOperations<String, Object> valueOp;
    @Autowired
    private ListOperations<String, Object> listOp;
    @Autowired
    private SetOperations<String, Object> setOp;
    @Autowired
    private ZSetOperations<String, Object> zSetOp;
    public  static  void putSet(String key,Object value){
            instance.setOp.add(key, value);
    }
    public  static  void removeSet(String key,Object value){
        instance.setOp.remove(key, value);
    }
    public  static Set<Object> getSet(String key){
        return  instance.setOp.members(key);
    }
    public  static  void putHash(String key,String hkey,Object value){
         instance.hashOp.put(key, hkey, value);
    }
    public  static  void removeHash(String key,String hkey){
        instance.hashOp.delete(key, hkey);
    }
    public  static  boolean containHash(String key,String hkey){
         return  instance.hashOp.hasKey(key,hkey);
    }
    public static  Object getHash(String key,String hkey){
        return  instance.hashOp.get(key, hkey);
    }
    public  static  void putString(String key,Object value){
        instance.valueOp.set(key, value);
    }
    public  static  void putString(String key, Object value, long time, TimeUnit timeUnit){
         instance.valueOp.set(key, value, time, timeUnit);
    }
    public  static  String get(String key){
        try {
            return  instance.valueOp.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }


    @Autowired
    public static void setInstance(RedisUtil instance) {
        RedisUtil.instance = instance;
    }

    public HashOperations<String, String, Object> getHashOp() {
        return hashOp;
    }

    public void setHashOp(HashOperations<String, String, Object> hashOp) {
        this.hashOp = hashOp;
    }

    public ValueOperations<String, Object> getValueOp() {
        return valueOp;
    }

    public void setValueOp(ValueOperations<String, Object> valueOp) {
        this.valueOp = valueOp;
    }

    public ListOperations<String, Object> getListOp() {
        return listOp;
    }

    public void setListOp(ListOperations<String, Object> listOp) {
        this.listOp = listOp;
    }

    public SetOperations<String, Object> getSetOp() {
        return setOp;
    }

    public void setSetOp(SetOperations<String, Object> setOp) {
        this.setOp = setOp;
    }

    public ZSetOperations<String, Object> getzSetOp() {
        return zSetOp;
    }

    public void setzSetOp(ZSetOperations<String, Object> zSetOp) {
        this.zSetOp = zSetOp;
    }
}
