package com.fzubb.service.impl;
import com.fzubb.constant.CacheKey;
import com.fzubb.mapper.StudentMapper;
import com.fzubb.model.dto.Student;
import com.fzubb.service.*;
import com.fzubb.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements  StudentService{
    @Autowired
    RedisTemplate<String,Object> cacheClient;

    @Autowired
    StudentMapper studentMapper;
    @Override
    public Student getInfo(String qqId) {
       /* 得到缓存key*/
        CacheKey cacheKey=CacheKey.Student_Info;
        String key=CacheKey.Student_Info.getKeyWithParams(qqId);

        /*先从缓存获取，没有从db获取，再放入缓存*/
        Student student= RedisUtil.get(cacheClient,key);
        if(student==null){
            student=studentMapper.get(qqId);
            RedisUtil.put(cacheClient, key, student,cacheKey.getTime(),cacheKey.getTimeUnit());
        }

        return   student;
    }

    @Override
    public Student updateInfo(Student student) {
        String qqId=student.getQqId();
        /* 得到缓存key*/
        CacheKey cacheKey=CacheKey.Student_Info;
        String key=CacheKey.Student_Info.getKeyWithParams(qqId);
        studentMapper.update(student);
        RedisUtil.put(cacheClient, key, student,cacheKey.getTime(),cacheKey.getTimeUnit());
        return student;
    }

    @Override
    public void deleteInfo(String qqId) {
        /* 得到缓存key*/
        String key=CacheKey.Student_Info.getKeyWithParams(qqId);
        studentMapper.delete(qqId);
        RedisUtil.del(cacheClient, key);
    }
}
