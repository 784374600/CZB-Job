package com.fzubb.api;

import com.fzubb.api.config.MqConfig;
import com.fzubb.dao.model.dto.Student;
import org.apache.dubbo.common.logger.Logger;
import org.apache.dubbo.common.logger.LoggerFactory;

import java.lang.reflect.Field;

public class Test{

   public static void main(String[] args){
         String[]  fields=getFieldNames(Student.class);
         for (String s:fields){
             System.out.print(s+",");
            //System.out.println( "<if test=\""+s +"!= null and "+s+" != ''\">,"+s+"</if>");
             //System.out.println( "<if test=\""+s +"!= null and "+s+" != ''\">, #{"+s+"} </if>");
         }
   }
   private  static  String[] getFieldNames(Class<?> c){
      Field[] fields=c.getDeclaredFields();
      String[] names=new String[fields.length];
      for(int i=0;i<fields.length;i++) {
         names[i] = fields[i].getName();
      }
      return  names;
   }


}
