package com.fzubb.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {
       public  static List<String> find(String content,String regex,int index){
           Pattern p=Pattern.compile(regex);
           List<String> list=new ArrayList<>();
           Matcher m=p.matcher(content);
           while (m.find()){
               list.add(m.group(index));
           }
           return  list;
       }
       public  static String findOne(String content,String regex,int index){
           Pattern p=Pattern.compile(regex);
           List<String> list=new ArrayList<>();
           Matcher m=p.matcher(content);
           if(m.find()){
              return  m.group(index);
           }
           return  null;
       }

}
