package com.fzubb.model.dto;

import lombok.Data;

import java.util.List;
@Data
public class Course {
    String id;
    String courseId;
    String name;
    String teacher;
    List<Teach> teachList;
    @Data
    public static  class  Teach{
         String place;
         int  week1;
         int week2;
         int  time1;
         int time2;
    }
}
