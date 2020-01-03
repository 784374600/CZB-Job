package com.fzubb.constant;

import java.text.MessageFormat;


public enum CacheKey {
    Baidu_Token("baidu_token_{0}","百度token 缓存key 参数：百度client_id",),
    QQ_Token("qq_token","qq Token 缓存key"),

    Student_Info("student_Info{0}","学生信息缓存 参数：学生qqId"),
    Student_Courses_Info("student_courses_info{0}","学生选课缓存 参数：qqId"),
    Course_Tasks_Info("student_course_tasks_{0}_{1}","学生课程任务缓存 参数 qqId; courseId ");


    String key;String desc;
    CacheKey(String key,String desc){
        this.key=key;this.desc=desc;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getKeyWithParams(String... params){
        if(params==null || params.length==0)
            return  getKey();
         return MessageFormat.format(getKey(),params);
      }

}
