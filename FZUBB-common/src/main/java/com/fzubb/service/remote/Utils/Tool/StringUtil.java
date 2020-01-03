package com.fzubb.service.remote.Utils.Tool;

public class StringUtil {
    public  static  boolean Empty(String s){
        if(s!=null && !s.equals("") )
            return  false;
        return  true;
    }
    public  static  boolean Empty(String... s){
        if(s==null)
            return  true;
        for(int i=0;i<s.length;i++){
            if(Empty(s[i]))
                return  true;
        }
        return  false;
    }
}
