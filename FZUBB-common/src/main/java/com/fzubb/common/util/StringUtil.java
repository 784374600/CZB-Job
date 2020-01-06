package com.fzubb.common.util;

public class StringUtil {
    public  static  boolean isEmpty(String s){
        if(s!=null && !s.equals("") )
            return  false;
        return  true;
    }
    public  static  boolean existEmpty(String... s){
        if(s==null)
            return  true;
        for(int i=0;i<s.length;i++){
            if(isEmpty(s[i]))
                return  true;
        }
        return  false;
    }
}
