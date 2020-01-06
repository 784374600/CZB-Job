package com.fzubb.api.service.base.impl;

import com.fzubb.common.constant.Constant;
import com.fzubb.common.constant.CacheKey;
import com.fzubb.dao.mapper.TaskMapper;
import com.fzubb.dao.model.dto.PublicTask;
import com.fzubb.dao.model.dto.PublicTaskId;
import com.fzubb.dao.model.dto.Task;
import com.fzubb.dao.querycondition.CommentCondition;
import com.fzubb.dao.querycondition.PublicTaskCondition;
import com.fzubb.common.util.IDUtil;
import com.fzubb.common.util.RedisUtil;
import com.fzubb.dao.model.dto.Comment;
import com.fzubb.api.service.base.TaskService;
import org.apache.dubbo.common.utils.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    RedisTemplate<String,Object> client;
    @Autowired
    TaskMapper taskMapper;
    @Override
    public List<Task> getTasks(String qqId) {
        CacheKey cacheKey=CacheKey.Student_Tasks_Info;
        String key=cacheKey.getKeyWithParams(qqId);
        List<Task> tasks= RedisUtil.hget(client,key ,qqId);
        if(tasks==null){
            tasks=taskMapper.getTasks(qqId);
            RedisUtil.hput(client, key, qqId, tasks);
        }
        return  tasks;
    }

    @Override
    public Task addTask(Task task) {
        CacheKey cacheKey=CacheKey.Student_Tasks_Info;
        String key=cacheKey.getKeyWithParams(task.getQqId());
        task.setTaskId(IDUtil.timeId());
        taskMapper.addTask(task);
        RedisUtil.hput(client, key, task.getTaskId(),task);
        return  task;
    }

    @Override
    public Task deleteTask(String qqId,long taskId) {
        CacheKey cacheKey=CacheKey.Student_Tasks_Info;
        String key=cacheKey.getKeyWithParams(qqId);
        taskMapper.deleteTask(qqId,taskId);
        RedisUtil.hdel(client, key, taskId);
        return null;
    }

    @Override
    public Task getTask(String qqId,long taskId) {
        CacheKey cacheKey=CacheKey.Student_Tasks_Info;
        String key=cacheKey.getKeyWithParams(qqId,taskId);
        Task task=RedisUtil.hget(client, key, taskId);
        if(task==null){
            task=taskMapper.getTask(qqId, taskId);
            RedisUtil.hput(client, key, taskId,task);
        }
        return  task;
    }

    @Override
    public PublicTask getPublicTask(String qqId, long taskId) {
        CacheKey cacheKey=CacheKey.PublicTask_Info;
        String key=cacheKey.getKeyWithParams(qqId,taskId);
        PublicTask publicTask=new PublicTask();
        Map<Object,Object> map=RedisUtil.hgetEntrys(client, key);
        if(map==null || map.size()==0){
            publicTask=taskMapper.getPublicTask(qqId,taskId);
            map=new HashMap<>();
            map.put("qqId",publicTask.getQqId());
            map.put("taskId",publicTask.getTaskId());
            map.put(Constant.PublicTask_Comments,publicTask.getCommentNum());
            map.put(Constant.PublicTask_Thumbs,publicTask.getThumbNum());
            map.put(Constant.PublicTask_LatestCommentTime,publicTask.getLatestCommentTime());
            RedisUtil.hputEntrys(client, key,map);
        }else{
            publicTask.setQqId(qqId);
            publicTask.setTaskId(taskId);
            publicTask.setCreateTime((long)map.get(Constant.PublicTask_CreateTime));
            publicTask.setCommentNum((int) map.get(Constant.PublicTask_Comments));
            publicTask.setThumbNum((int) map.get(Constant.PublicTask_Thumbs));
            publicTask.setLatestCommentTime((long) map.get(Constant.PublicTask_LatestCommentTime));
        }
        return publicTask;
    }

    @Override
    public List<PublicTask> getPublicTasks(String qqId) {
        return taskMapper.getOwnPublicTasks(qqId);
    }

    @Override
    public List<PublicTask> getPublicTasksByCondition(PublicTaskCondition condition) {
        List<PublicTaskId>  ids=taskMapper.getPublicTaskIdsByCourse(Long.parseLong(IDUtil.getTime(0,8)+999999),condition);
        if(CollectionUtils.isEmpty(ids)) {
            return null;
        }
        List<PublicTask> publicTasks=new ArrayList<>();
        for (PublicTaskId id : ids
        ) {
            publicTasks.add(getPublicTask(id.getQqId(), id.getTaskId()));
        }
        return  publicTasks;
    }




    @Override
    public PublicTask addPublicTask(PublicTask task) {
        taskMapper.addPublicTask(task);
        PublicTask publicTask=new PublicTask();
        publicTask.setTaskId(task.getTaskId());
        return publicTask;
    }

    @Override
    public PublicTask deletePublicTask(String qqId,long taskId) {
        taskMapper.deletePublicTask(qqId,taskId);
        return null;
    }

    @Override
    public Comment addComment(Comment comment) {
        comment.setCommentId(IDUtil.timeIdWithParam(String.valueOf((int)((Math.random()*9+1)*1000))));
        taskMapper.addComment(comment);
        return comment;
    }

    @Override
    public Comment deleteComment(String qqId,long taskId,long commentId) {
        taskMapper.deleteComment(qqId, taskId, commentId);
        return  null;
    }

    @Override
    public List<Comment> getComments(String qqId, long taskId, CommentCondition condition) {
        CacheKey commentCacheKey=CacheKey.Task_Comments_OrderByTime;
        String commentKey=commentCacheKey.getKeyWithParams(qqId,taskId);
        int num=condition.getNum();
        List<Comment> comments=new ArrayList<>();Set<Comment> cacheResult;
        long lastCommentId=condition.getLastCommentId();
        if(lastCommentId==0){
            cacheResult=RedisUtil.zgetrev(client,commentKey,0, num);
        } else{
            cacheResult=RedisUtil.zgetrevByScore(client, commentKey, 0, lastCommentId, 1,num);
        }
        if(!CollectionUtils.isEmpty(cacheResult)){
            comments.addAll(cacheResult);
        }
        if(comments.size()<num){
            long last=0;
            if(comments.size()>0) {
                last = comments.get(comments.size() - 1).getCommentId();
            }
            condition.setLastCommentId(last);condition.setNum(num-comments.size());
            comments=taskMapper.getComments(qqId, taskId, condition);
            /*此处或许可以做优化，用消息队列来加快速度*/
            for (Comment comment:comments){
                RedisUtil.zput(client,commentKey, comment, comment.getCommentId());
            }
        }
        return comments;
    }
}
