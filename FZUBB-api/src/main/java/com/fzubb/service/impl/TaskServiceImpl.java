package com.fzubb.service.impl;

import com.fzubb.querycondition.CommentCondition;
import com.fzubb.constant.CacheKey;
import com.fzubb.mapper.TaskMapper;
import com.fzubb.model.dto.Comment;
import com.fzubb.model.dto.PublicTask;
import com.fzubb.model.dto.Task;
import com.fzubb.model.vo.PublicTaskVo;
import com.fzubb.model.vo.TaskVo;
import com.fzubb.service.TaskService;
import com.fzubb.util.IDUtil;
import com.fzubb.util.RedisUtil;
import org.apache.dubbo.common.utils.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    RedisTemplate<String,Object> client;
    @Autowired
    TaskMapper taskMapper;
    @Override
    public List<Task> getTasksByCourse(String qqId, String courseId) {
        CacheKey cacheKey=CacheKey.Student_Course_Tasks_Info;
        String key=cacheKey.getKeyWithParams(qqId);
        List<Task> tasks= RedisUtil.hget(client,key , courseId);
        if(tasks==null){
            tasks=taskMapper.getTasksByCourse(qqId, courseId);
            RedisUtil.hput(client, key, courseId, tasks);
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
    public Task deleteTask(Task task) {
        CacheKey cacheKey=CacheKey.Student_Tasks_Info;
        String key=cacheKey.getKeyWithParams(task.getQqId());
        taskMapper.deleteTask(task);
        RedisUtil.hdel(client, key, task.getTaskId());
        return  task;
    }

    @Override
    public TaskVo getTaskVo(Task task) {
        CacheKey cacheKey=CacheKey.Task_Answer_Info;
        String key=cacheKey.getKeyWithParams(task.getQqId(),task.getTaskId());
        TaskVo taskVo=RedisUtil.hget(client, key, task.getTaskId());
        if(taskVo==null){
            taskVo=taskMapper.getTask(task.getQqId(), task.getTaskId());
            RedisUtil.hput(client, key, task.getTaskId(),taskVo);
        }
        return  taskVo;
    }

    @Override
    public List<PublicTask> getPublicTasks(String qqId) {
        return taskMapper.getPublishTask(qqId);
    }

    @Override
    public PublicTaskVo getPublicTaskVo(PublicTask publicTask, CommentCondition condition) {
        CacheKey commentCacheKey=CacheKey.Task_Comments_OrderByTime;
        String commentKey=commentCacheKey.getKeyWithParams(publicTask.getQqId(),publicTask.getTaskId());
        int page=condition.getPage();int num=condition.getNum();
        List<Comment> comments=new ArrayList<>(RedisUtil.zgetrev(client, commentKey,page*num,(page+1)*num-1));
        if(CollectionUtils.isEmpty(comments)){
            comments=taskMapper.get
        }
        TaskVo taskVo=RedisUtil.zput(client, key, task.getTaskId());
        return taskMapper.getPublicTaskVo(publicTask);
    }

    @Override
    public List<PublicTask> getPublicTaskByCourse(String courseId) {
        return null;
    }

    @Override
    public PublicTask addPublicTask(Task task) {
        taskMapper.addPublicTask(task);
        return null;
    }

    @Override
    public PublicTask deletePublicTask(Task task) {
        taskMapper.deletePublicTask(task);
        return null;
    }

    @Override
    public Comment addComment(Comment comment) {
        comment.setCommentId(IDUtil.timeIdWithParam(String.format("%04d", (int)((Math.random()*9+1)*1000))));
        taskMapper.addComment(comment);
        return comment;
    }

    @Override
    public Comment deleteComment(Comment comment) {
        taskMapper.deleteComment(comment);
        return  null;
    }
}
