package com.fzubb.api.dubboservice;

import com.fzubb.common.constant.CacheKey;
import com.fzubb.common.constant.Constant;
import com.fzubb.dao.model.dto.*;
import com.fzubb.client.request.BaseRequest;
import com.fzubb.common.remote.PhotoService;
import com.fzubb.common.remote.SchoolSystemService;
import com.fzubb.common.util.RedisUtil;
import com.fzubb.client.dubboservice.FZUBBWriteService;
import com.fzubb.client.response.BaseResponse;
import com.fzubb.api.service.base.CourseService;
import com.fzubb.api.service.base.StudentService;
import com.fzubb.api.service.base.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FZUBBWriteServiceImpl implements FZUBBWriteService {
    @Autowired
    StudentService studentService;
    @Autowired
    SchoolSystemService schoolSystemService;
    @Autowired
    CourseService courseService;
    @Autowired
    TaskService taskService;
    @Autowired
    PhotoService photoService;
    @Autowired
    RedisTemplate<String,Object> client;
    @Override
    public BaseResponse<Object> bindStudent(BaseRequest request) {
        checkParams(request,"id","psw");
        String qqId=request.getQqId(); String id=request.getParam("id");String psw=request.getParam("psw");
        Student student=schoolSystemService.getStudentInfo(id,psw);
        student.setQqId(qqId);
        student=studentService.updateInfo(student);
        List<Course> courses=schoolSystemService.getCoursesInfo(id,psw);
        courseService.updateSC(qqId,courses);
        Map<String,Object> map=new HashMap<>();
        map.put("student",student);
        map.put("courses", courses);
        return BaseResponse.success(map);
    }

    @Override
    public BaseResponse<Object> unbindStudent(BaseRequest request) {
        checkParams(request);
        String qqId=request.getQqId();
        studentService.deleteInfo(qqId);
        courseService.deleteSC(qqId);
        return BaseResponse.success(null);
    }

    @Override
    public BaseResponse<Task> addTask(BaseRequest request) {
        checkParams(request,"taskId");
        return BaseResponse.success(taskService.addTask(request.getParam("task",Task.class)));
    }

    @Override
    public BaseResponse<Task> deleteTask(BaseRequest request) {
         checkParams(request,"taskId");
         return BaseResponse.success(taskService.deleteTask(request.getQqId(),request.getParam("taskId",long.class)));
    }

    @Override
    public BaseResponse<PublicTask> addPublicTask(BaseRequest request) {
        checkParams(request,"taskId");
        String qqId=request.getQqId();long taskId=request.getParam("taskId",long.class);
        PublicTask publicTask=new PublicTask();
        publicTask.setQqId(qqId);publicTask.setTaskId(taskId);
        return BaseResponse.success(taskService.addPublicTask(publicTask));
    }

    @Override
    public BaseResponse<PublicTask> deletePublicTask(BaseRequest request) {
        checkParams(request,"taskId");
        String qqId=request.getQqId();long taskId=request.getParam("taskId",long.class);
        return BaseResponse.success(taskService.deletePublicTask(qqId,taskId));
    }

    @Override
    public BaseResponse<Comment> addComment(BaseRequest request) {
        checkParams(request,"taskId","content");
        Comment comment= Comment.builder()
                .qqId(request.getQqId()).taskId(request.getParam("taskId",long.class))
                .content(request.getParam("content")).build();
        comment=  taskService.addComment(comment);
        CacheKey cacheKey=CacheKey.PublicTask_Info;
        String key=cacheKey.getKeyWithParams(request.getQqId(),request.getParam("taskId",long.class));
        RedisUtil.hinc(client,key, Constant.PublicTask_Thumbs,1);
        return BaseResponse.success(comment);
    }

    @Override
    public BaseResponse<Object> deleteComment(BaseRequest request) {
        checkParams(request,"taskId","commentId");
        String qqId=request.getQqId();long taskId=request.getParam("taskId",long.class);
        long commentId=request.getParam("commentId",long.class);
        taskService.deleteComment(qqId,taskId,commentId);
        CacheKey cacheKey=CacheKey.PublicTask_Info;
        String key=cacheKey.getKeyWithParams(qqId,taskId);
        RedisUtil.hinc(client,key, Constant.PublicTask_Thumbs,-1);
        return BaseResponse.success(null);
    }

    @Override
    public BaseResponse<Object> thumbOprate(BaseRequest request) {
        checkParams(request,"taskId","operation");
        String qqId=request.getQqId();long taskId=request.getParam("taskId",long.class);
        long operation=request.getParam("operation",long.class);

        CacheKey lockKey=CacheKey.Thumb_Lock;
        String lock=lockKey.getKeyWithParams(qqId);
        if(RedisUtil.exist(client,lock)){
            return  BaseResponse.operate_freequent("点赞");
        }
        RedisUtil.put(client, lock,1);

        CacheKey cacheKey=CacheKey.PublicTask_Info;
        String key=cacheKey.getKeyWithParams(qqId,taskId);
        RedisUtil.hinc(client,key, Constant.PublicTask_Thumbs,operation);
        RedisUtil.del(client,lock);

        return BaseResponse.success(null);
    }

    @Override
    public BaseResponse<Object> uploadPhoto(BaseRequest request) {
        String qqId=request.getQqId();
        byte[] photo=request.getParam("photo",byte[].class);
        String name=request.getParam("name");
        return BaseResponse.success(photoService.upload(photo,name,qqId).url());
    }

    @Override
    public BaseResponse<Object> delPhoto(BaseRequest request) {
        /*此处可以改进，改成发送给一条删除图片的消息*/
        String url=request.getParam("url");
        photoService.delete(url);
        return BaseResponse.success(null);
    }
}
