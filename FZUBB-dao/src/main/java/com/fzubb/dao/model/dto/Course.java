package com.fzubb.dao.model.dto;

import lombok.Data;
import org.apache.dubbo.common.utils.CollectionUtils;

import java.util.HashSet;
import java.util.List;

public class Course {
    String courseId;
    String name;
    String teacher;
    List<Teach> teachList;
    int  week1;
    int week2;
    Integer type=0;//0表示每周，1表示单周上课，2表示双周上课

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof  Course){
            Course c=(Course)obj;
            return  courseId.equals(c.getCourseId()) && teacher.equals(c.getTeacher()) && week1==c.getWeek1();
        }
        return  false;
    }

    public static  class  Teach{
         String place;
         int  time1;
         int time2;
         int day;

        public String getPlace() {
            return place;
        }

        public void setPlace(String place) {
            this.place = place;
        }


        public int getTime1() {
            return time1;
        }

        public void setTime1(int time1) {
            this.time1 = time1;
        }

        public int getTime2() {
            return time2;
        }

        public void setTime2(int time2) {
            this.time2 = time2;
        }

        public int getDay() {
            return day;
        }

        public void setDay(int day) {
            this.day = day;
        }
    }

    public int getWeek1() {
        return week1;
    }

    public void setWeek1(int week1) {
        this.week1 = week1;
    }

    public int getWeek2() {
        return week2;
    }

    public void setWeek2(int week2) {
        this.week2 = week2;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public List<Teach> getTeachList() {
        return teachList;
    }

    public void setTeachList(List<Teach> teachList) {
        this.teachList = teachList;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
