package com.fzubb.user.service;

import com.fzubb.common.constant.CacheKey;
import com.fzubb.common.remote.SchoolSystemService;
import com.fzubb.common.util.RedisUtil;
import com.fzubb.user.mapper.StudentMapper;
import com.fzubb.user.model.Student;
import com.fzubb.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
/**
 * @author caizhibin
 */
public class StudentServiceImpl implements UserService<Student>   {
    @Autowired
    RedisTemplate<String,Object> cacheClient;

    @Autowired
    StudentMapper studentMapper;
    @Autowired
    SchoolSystemService schoolSystemService;

    @Override
    public Student query(Student student) {
        String qqId=student.getQqId();
        /* 得到缓存key*/
        CacheKey cacheKey=CacheKey.Student_Info;
        String key=CacheKey.Student_Info.getKeyWithParams(qqId);

        /*先从缓存获取，没有从db获取，再放入缓存*/
        student= RedisUtil.get(cacheClient,key);
        if(student==null){
            student=studentMapper.get(qqId);
            RedisUtil.put(cacheClient, key, student,cacheKey.getTime(),cacheKey.getTimeUnit());
        }

        return   student;
    }

    @Override
    public Student bind(Student student) {
        String qqId=student.getQqId();
        /* 得到缓存key*/
        CacheKey cacheKey=CacheKey.Student_Info;
        String key=CacheKey.Student_Info.getKeyWithParams(qqId);
        studentMapper.update(student);
        RedisUtil.put(cacheClient, key, student,cacheKey.getTime(),cacheKey.getTimeUnit());
        return student;
    }

    @Override
    public Student unbind(Student student) {
        String qqId=student.getQqId();
        /* 得到缓存key*/
        String key=CacheKey.Student_Info.getKeyWithParams(qqId);
        studentMapper.delete(qqId);
        RedisUtil.del(cacheClient, key);
        return  null;
    }
}
