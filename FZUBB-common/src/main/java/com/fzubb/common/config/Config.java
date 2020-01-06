package com.fzubb.common.config;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public interface Config  {
    default Map<String,Object>  params(){
        Map<String,Object>  params=new HashMap<>();
        Field[] fields=this.getClass().getDeclaredFields();
        for (Field field:fields){
            field.setAccessible(true);
            try {
                params.put(field.getName(), field.get(this));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return  params;
    };
}
