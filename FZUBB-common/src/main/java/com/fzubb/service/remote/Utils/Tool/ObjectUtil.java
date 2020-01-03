package com.fzubb.service.remote.Utils.Tool;

public class ObjectUtil {
    public  static  boolean Empty(Object ...objects){
        if (objects==null)
            return  true;
        for (Object object:objects){
            if (object==null)
                return true;
        }
        return  false;
    }
}
