package com.fzubb.model.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.util.StringUtils;

@Data
@Builder
public class Student {
     String qqId;
     String nickName;
     String avator;

     String name;
     String id;
     String psw;
     String colleage;
     String major;
     String age;
     String sex;
     String grade;
     String place;
     public  boolean unbind(){
          return StringUtils.isEmpty(id);
     }
}
