package com.fzubb.constant;

import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;


public enum CacheKey {
    Baidu_Token("baidu_token_{0}","百度token 缓存key 参数：百度client_id"),
    QQ_Token("qq_token","qq Token 缓存key"),

    Student_Info("student_Info_{0}","学生信息缓存 参数：学生qqId"),

    Courses_Info("courses_info_{0}_{1}","所有课程信息缓存 参数：courseId;teacher"),
    Student_Courses_Info("student_courses_info{0}","学生选课缓存 参数：qqId"),

    Student_Tasks_Info("student_tasks_info_{0}","学生任务缓存 hashkey 参数：qqId ; hkey:taskId"),
    Student_Course_Tasks_Info("student_course_tasks_{0}","hash键 学生课程任务缓存 参数 qqId;  hashkey 课程id"),

    PublicTask_Info("publictask_info_{0}_{1}","公开任务缓存 hash键 参数： qqId,taskId"),

    Task_Answer_Info("student_task_answer_info_{0}_{1}","hash键 学生错误答案与正确答案集合 参数：qqId,taskId hkey:wrong,right"),

    Task_Comments_OrderByTime("task_comments_OrderByTime_{0}_{1}","zset 参数：qqId,taskId")
    ;



    String key;String desc;long time;TimeUnit timeUnit;
    CacheKey(String key,String desc){
        this.key=key;this.desc=desc; this.time=0;
    }
    CacheKey(String key,String desc,long time,TimeUnit timeUnit){
        this.key=key;this.desc=desc; this.time=0;this.timeUnit=timeUnit;
    }



    public String getKeyWithParams(Object... params){
        if(params==null || params.length==0)
            return  getKey();
         return MessageFormat.format(getKey(),params);
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

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }
}
