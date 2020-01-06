package com.fzubb.api.service.base.impl;

import com.alibaba.fastjson.JSON;
import com.fzubb.common.ExcepTion.ServerNotServeException;
import com.fzubb.common.constant.CacheKey;
import com.fzubb.common.constant.MQConstant;
import com.fzubb.dao.mapper.CourseMapper;
import com.fzubb.dao.model.dto.Course;
import com.fzubb.common.mq.Message.UpdateSCMessage;
import com.fzubb.common.util.RedisUtil;
import com.fzubb.api.service.base.CourseService;
import org.apache.dubbo.common.logger.Logger;
import org.apache.dubbo.common.logger.LoggerFactory;
import org.apache.dubbo.common.utils.CollectionUtils;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {
    private  static Logger logger= LoggerFactory.getLogger(CourseServiceImpl.class);
    @Autowired
    DefaultMQProducer producer;
    @Autowired
    RedisTemplate<String,Object> cacheClient;
    @Autowired
    CourseMapper courseMapper;
    private static String courseskey=CacheKey.Courses_Info.getKey();

    @Override
    public List<Course> getCourses(String qqId) {
        CacheKey cacheKey=CacheKey.Student_Courses_Info;
        String key=cacheKey.getKeyWithParams(qqId);
        //id为courseId_teacher_week1
        List<String > ids= RedisUtil.get(cacheClient, key);
        if(CollectionUtils.isEmpty(ids)){
            ids=courseMapper.getCourses(qqId);
            RedisUtil.put(cacheClient, key, ids,cacheKey.getTime(),cacheKey.getTimeUnit());
        }
        List<Course> courses=null;
        if(!CollectionUtils.isEmpty(ids)){
            courses=new ArrayList<>(ids.size());
            for(String id:ids) {
                courses.add(RedisUtil.hget(cacheClient,courseskey, id));
            }
        }

        return courses;
    }

    @Override
    public List<Course> updateCourses(List<Course> courses) {
        if (!CollectionUtils.isEmpty(courses)) {
            for(Course course:courses) {
                String id=course.getCourseId()+"_"+course.getTeacher()+"_"+course.getWeek1();
                RedisUtil.hput(cacheClient, courseskey, id,course);
            }
        }
        return  courses;
    }



    @Override
    public void deleteSC(String qqId) {
        CacheKey cacheKey=CacheKey.Student_Courses_Info;
        String key=cacheKey.getKeyWithParams(qqId);
        RedisUtil.del(cacheClient, key);
        try {
            courseMapper.deleteSC(qqId);
        } catch (Exception e) {
            logger.warn(MessageFormat.format("删除学生{0}选课信息失败",qqId));
        }
    }

    @Override
    public void updateSC(String qqId, List<Course> courses) {
        CacheKey cacheKey=CacheKey.Student_Courses_Info;
        String key=cacheKey.getKeyWithParams(qqId);
        if (!CollectionUtils.isEmpty(courses)) {
            List<String> ids=courses.stream().map((c)->c.getCourseId()+"_"+c.getTeacher()+"_"+c.getWeek1()).collect(Collectors.toList());
                RedisUtil.put(cacheClient, key, ids);
        }

        updateCourses(courses);

        /*发送学生更新课表及选课信息*/
        UpdateSCMessage msg=new UpdateSCMessage();
        msg.setId(qqId);msg.setCourses(courses);
        try {
            producer.send(new Message(MQConstant.FZUBB_Topic, MQConstant.Update_Course_Tag, JSON.toJSONBytes(msg)));
        } catch (MQClientException e) {
            logger.warn("发送更新课表消息失败，客户端异常 学号："+qqId);
            throw  new ServerNotServeException("课表更新消息发送异常");
        } catch (RemotingException e) {
            logger.warn("发送更新课表消息失败，remoting异常 学号："+qqId);
            throw  new ServerNotServeException("课表更新消息发送异常");
        } catch (MQBrokerException e) {
            logger.warn("发送更新课表消息失败，broker异常 学号："+qqId);
            throw  new ServerNotServeException("课表更新消息发送异常");
        } catch (InterruptedException e) {
            logger.warn("发送更新课表消息失败，interrupted异常 学号："+qqId);
            throw  new ServerNotServeException("课表更新消息发送异常");
        }
        try {
            courseMapper.updateSC(qqId, courses);
        } catch (Exception e) {
            e.printStackTrace();
            logger.warn(MessageFormat.format("更新学生{0}选课信息失败",qqId));
        }
    }
}
