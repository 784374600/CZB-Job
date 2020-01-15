package com.fzubb.user.model;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;
import org.springframework.util.StringUtils;

@Data
@Builder
public class Student extends User{
     String qqId;
     String nickName;
     String avator;

     String name;
     String id;
     String psw;
     String colleage;
     String major;
     String sex;
     String grade;
     String place;
     @Tolerate
     public Student() {
     }

     public  boolean unbind(){
          return StringUtils.isEmpty(id);
     }
}
