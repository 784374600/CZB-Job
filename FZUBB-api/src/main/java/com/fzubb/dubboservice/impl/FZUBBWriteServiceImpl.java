package com.fzubb.dubboservice.impl;

import com.fzubb.dubboservice.FZUBBWriteService;
import com.fzubb.model.dto.*;
import com.fzubb.service.*;
import com.fzubb.model.request.BaseRequest;
import com.fzubb.model.response.BaseResponse;
import com.fzubb.service.remote.SchoolSystemService;
import org.springframework.beans.factory.annotation.Autowired;

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
    @Override
    public BaseResponse<Object> bindStudent(BaseRequest request) {
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
        String qqId=request.getQqId();
        studentService.deleteInfo(qqId);
        courseService.deleteSC(qqId);
        return BaseResponse.success(null);
    }

    @Override
    public BaseResponse<Task> addTask(BaseRequest request) {
        return BaseResponse.success(taskService.addTask(request.getParam("task",Task.class)));
    }

    @Override
    public BaseResponse<Task> deleteTask(BaseRequest request) {
         return BaseResponse.success(taskService.deleteTask(request.getParam("task",Task.class)));
    }

    @Override
    public BaseResponse<PublicTask> addPublicTask(BaseRequest request) {
        return BaseResponse.success(taskService.addPublicTask(request.getParam("task",Task.class)));
    }

    @Override
    public BaseResponse<PublicTask> deletePublicTask(BaseRequest request) {
        return BaseResponse.success(taskService.deletePublicTask(request.getParam("task",Task.class)));
    }

    @Override
    public BaseResponse<Comment> addComment(BaseRequest request) {
        return BaseResponse.success(taskService.addComment(request.getParam("task",Comment.class)));
    }

    @Override
    public BaseResponse<Comment> deleteComment(BaseRequest request) {
        return BaseResponse.success(taskService.deleteComment(request.getParam("task",Comment.class)));
    }
}
