package com.fzubb.common.util;


import java.util.Map;

public class FormatUtil {
    public  static String
    formData(Map<String,Object> params){

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



}
