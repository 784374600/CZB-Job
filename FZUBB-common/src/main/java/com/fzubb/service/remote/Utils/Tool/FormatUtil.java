package com.fzubb.service.remote.Utils.Tool;


import com.alibaba.fastjson.JSONObject;
import net.sf.json.JSONArray;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class FormatUtil {
    public  static String
    FormData(Map<String,Object> params){

        if (params==null)
            return  null;
        int size=params.size();
        StringBuilder content=new StringBuilder();
        int i=1;
        for (Map.Entry<String,Object> entry:params.entrySet()
             ) {
            content.append(entry.getKey()+"="+entry.getValue());
               if((i++)!=size)
                content.append("&");
        }
        return  content.toString();
    }
    public  static JSONObject  getJson(String json){
           return  JSONObject.parseObject(json);
    }
    public  static  Map<String,Object> getConfigParams(Object config){
         Map<String,Object>  params=new HashMap<>();
         Class c=config.getClass();
         Field[] fields=c.getDeclaredFields();
         for (Field field:fields){
             field.setAccessible(true);
             try {
                 params.put(field.getName(), field.get(config));
             } catch (IllegalAccessException e) {
                 e.printStackTrace();
             }
         }
         return  params;
    }
    public  static  String getFormStringByConfig(Object config){
           return  FormData(getConfigParams(config));
    }
    public static  void  print(String name,Object... objects){
        System.out.print(name+":{");
        int i=0;
        for (Object object :objects){
            System.out.print("参数"+(++i)+":"+object+" ");
        }
        System.out.println();
    }
    public  static Object[] getStringArray(String jsonArray,Class<?> c){
        JSONArray array=null;
        if(!StringUtil.Empty(jsonArray))
            array=net.sf.json.JSONArray.fromObject(jsonArray);
        if(array==null)
            return  null;
        Object arr= Array.newInstance(c, array.size());

        for(int i=0;i<array.size();i++) {
          Array.set(arr, i, array.get(i));
        }
        return  (Object[])arr;
    }
    public  static  String JsonFy(Map<String,Object> params){
        net.sf.json.JSONObject jsonObject= net.sf.json.JSONObject.fromObject(params);
        return  jsonObject.toString();
    }
}
