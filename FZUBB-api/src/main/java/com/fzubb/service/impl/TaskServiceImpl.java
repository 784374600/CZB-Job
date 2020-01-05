package com.fzubb.service.impl;

import com.fzubb.constant.Constant;
import com.fzubb.model.dto.PublicTaskId;
import com.fzubb.model.vo.HomeDataPublicTaskVo;
import com.fzubb.querycondition.CommentCondition;
import com.fzubb.constant.CacheKey;
import com.fzubb.mapper.TaskMapper;
import com.fzubb.model.dto.Comment;
import com.fzubb.model.dto.PublicTask;
import com.fzubb.model.dto.Task;
import com.fzubb.model.vo.PublicTaskVo;
import com.fzubb.model.vo.TaskVo;
import com.fzubb.querycondition.PublicTaskCondition;
import com.fzubb.service.TaskService;
import com.fzubb.util.IDUtil;
import com.fzubb.util.RedisUtil;
import org.apache.dubbo.common.utils.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    public Task deleteTask(Task task) {
        CacheKey cacheKey=CacheKey.Student_Tasks_Info;
        String key=cacheKey.getKeyWithParams(task.getQqId());
        taskMapper.deleteTask(task);
        RedisUtil.hdel(client, key, task.getTaskId());
        return  task;
    }

    @Override
    public TaskVo getTaskVo(String qqId,long taskId) {
        CacheKey cacheKey=CacheKey.Task_Answer_Info;
        String key=cacheKey.getKeyWithParams(qqId,taskId);
        TaskVo taskVo=RedisUtil.hget(client, key, taskId);
        if(taskVo==null){
            taskVo=taskMapper.getTaskVo(qqId, taskId);
            RedisUtil.hput(client, key, taskId,taskVo);
        }
        return  taskVo;
    }

    @Override
    public PublicTask getPublicTask(String qqId, long taskId) {
        CacheKey cacheKey=CacheKey.PublicTask_Info;
        String key=cacheKey.getKeyWithParams(qqId,taskId);
        PublicTask publicTask=new PublicTask();
        Map<Object,Object> map=RedisUtil.hgetEntrys(client, key);
        if(map==null || map.size()==0){
            publicTask=taskMapper.getPublicTask(qqId,taskId);
        }else{
            publicTask.setQqId(qqId);
            publicTask.setTaskId(taskId);
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
    public PublicTaskVo getPublicTaskVo(PublicTask publicTask, CommentCondition condition) {
        PublicTaskVo publicTaskVo=new PublicTaskVo();

        CacheKey commentCacheKey=CacheKey.Task_Comments_OrderByTime;
        String commentKey=commentCacheKey.getKeyWithParams(publicTask.getQqId(),publicTask.getTaskId());
        int num=condition.getNum();String qqId=publicTask.getQqId();long taskId=publicTask.getTaskId();
        List<Comment> comments=new ArrayList<>();Set<Comment> cacheResult;
        long lastCommentId=condition.getLastCommentId();
        if(lastCommentId==0)
            cacheResult=RedisUtil.zgetrev(client,commentKey,0, num);
        else
            cacheResult=RedisUtil.zgetrevByScore(client, commentKey, 0, lastCommentId, 1,num);
        if(!CollectionUtils.isEmpty(cacheResult))
            comments.addAll(cacheResult);
        if(comments.size()<num){
             long last=0;
             if(comments.size()>0)
                 last=comments.get(comments.size()-1).getCommentId();
            condition.setLastCommentId(last);condition.setNum(num-comments.size());
            comments=taskMapper.getComments(publicTask.getQqId(), publicTask.getTaskId(), condition);
            /*此处或许可以做优化，用消息队列来加快速度*/
            for (Comment comment:comments){
                RedisUtil.zput(client,commentKey, comment, comment.getCommentId());
            }
        }
        publicTaskVo.setComments(comments);

        /*当首次点入详情页，添加任务详情，添加点赞，评论数等信息*/
        if(lastCommentId==0){
            TaskVo taskVo=getTaskVo(qqId,taskId);
            publicTaskVo.setTask(taskVo);
            publicTask=getPublicTask(qqId, taskId);
        }
        publicTaskVo.setPublicTask(publicTask);
        return publicTaskVo;
    }

    @Override
    public List<HomeDataPublicTaskVo> getHomeDataPublicTaskByCourse(String courseId, PublicTaskCondition condition) {

        List<PublicTaskId>   ids=taskMapper.getPublicTaskIdsByCourse(courseId, condition);
        if(CollectionUtils.isEmpty(ids))
            return  null;
        List<HomeDataPublicTaskVo> homeDataPublicTaskVos=new ArrayList<>();
        for (PublicTaskId id : ids
        ) {
            HomeDataPublicTaskVo vo=new HomeDataPublicTaskVo();
            vo.setTask(getTaskVo(id.getQqId(),id.getTaskId()));
            vo.setPublicTask(getPublicTask(id.getQqId(), id.getTaskId()));
            homeDataPublicTaskVos.add(vo);
        }
        return  homeDataPublicTaskVos;
    }

    @Override
    public PublicTask addPublicTask(Task task) {
        taskMapper.addPublicTask(task);
        PublicTask publicTask=new PublicTask();
        publicTask.setTaskId(task.getTaskId());
        return publicTask;
    }

    @Override
    public PublicTask deletePublicTask(Task task) {
        taskMapper.deletePublicTask(task);
        return null;
    }

    @Override
    public Comment addComment(Comment comment) {
        comment.setCommentId(IDUtil.timeIdWithParam(String.valueOf((int)((Math.random()*9+1)*1000))));
        taskMapper.addComment(comment);
        return comment;
    }

    @Override
    public Comment deleteComment(Comment comment) {
        taskMapper.deleteComment(comment);
        return  null;
    }
}
