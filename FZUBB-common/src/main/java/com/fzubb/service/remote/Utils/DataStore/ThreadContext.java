package com.fzubb.service.remote.Utils.DataStore;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ThreadContext {
     private static Map<Thread, Map<String,Object>> threadContext=new ConcurrentHashMap<>();
     public  static Object get(String key){
          Map<String,Object> map=getMap();
          if(map==null)
              return  null;
          if(map.get(key)==null)
              return null;
          return  map.get(key);
     }
     public  static  void put(String key,Object object){
           Map<String,Object> map=getMap();
           map.put(key, object);
     }
     private   static Map<String,Object> getMap(){
          Map<String,Object> map=threadContext.get(Thread.currentThread());
          if(map==null){
               map=new HashMap<>();
               threadContext.put(Thread.currentThread(), map);
          }
          return  map;
     }
     public  static void remove(){
           threadContext.remove(Thread.currentThread());
     }

}
