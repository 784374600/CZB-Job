package com.fzubb.service.impl;

import com.alibaba.fastjson.JSON;
import com.fzubb.ExcepTion.ServerNotServeException;
import com.fzubb.constant.CacheKey;
import com.fzubb.constant.MQConstant;
import com.fzubb.mapper.CourseMapper;
import com.fzubb.model.dto.Course;
import com.fzubb.mq.Message.UpdateSCMessage;
import com.fzubb.service.CourseService;
import com.fzubb.util.RedisUtil;
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
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.List;
@Service
public class CourseServiceImpl implements CourseService {
    private  static Logger logger= LoggerFactory.getLogger(CourseServiceImpl.class);
    @Autowired
    DefaultMQProducer producer;
    @Autowired
    RedisTemplate<String,Object> cacheClient;
    @Autowired
    CourseMapper courseMapper;
    private static CacheKey coursesCacheKey=CacheKey.Student_Courses_Info;

    @Override
    public List<Course> getCourses(String qqId) {
        String key=coursesCacheKey.getKeyWithParams(qqId);
        List<Course> courses= RedisUtil.get(cacheClient, key);
        if(CollectionUtils.isEmpty(courses)){
            courses=courseMapper.getCourses(qqId);
            RedisUtil.put(cacheClient, key, courses,coursesCacheKey.getTime(),coursesCacheKey.getTimeUnit());
        }
        return courses;
    }

    @Override
    public List<Course> updateCourses(List<Course> courses) {
        RedisUtil.sput(cacheClient, CacheKey.Courses_Info.getKeyWithParams(), courses.toArray());
        return courses;
    }



    @Override
    public void deleteSC(String qqId) {
        String key=coursesCacheKey.getKeyWithParams(qqId);
        RedisUtil.del(cacheClient, key);
        try {
            courseMapper.deleteSC(qqId);
        } catch (Exception e) {
            logger.warn(MessageFormat.format("删除学生{0}选课信息失败",qqId));
        }
    }

    @Override
    public void updateSC(String qqId, List<Course> courses) {
        String key=coursesCacheKey.getKeyWithParams(qqId);
        RedisUtil.put(cacheClient, key, courses,coursesCacheKey.getTime(),coursesCacheKey.getTimeUnit());
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
