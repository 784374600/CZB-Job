package com.fzubb.dubboservice.impl;

import com.fzubb.dubboservice.FZUBBWriteService;
import com.fzubb.dubboservice.service.CourseService;
import com.fzubb.dubboservice.service.StudentInfoService;
import com.fzubb.dubboservice.service.TaskService;
import com.fzubb.model.dto.Comment;
import com.fzubb.model.dto.Course;
import com.fzubb.model.dto.Student;
import com.fzubb.model.dto.Task;
import com.fzubb.model.request.BaseRequest;
import com.fzubb.model.response.BaseResponse;
import com.fzubb.service.remote.SchoolSystemService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class FZUBBWriteServiceImpl implements FZUBBWriteService {
    @Autowired
    StudentInfoService studentInfoService;
    @Autowired
    SchoolSystemService schoolSystemService;
    @Autowired
    CourseService courseService;
    @Autowired
    TaskService taskService;
    @Override
    public BaseResponse<Object> bindStudent(BaseRequest request) {
        String qqId=request.getQqId(); String id=request.getParam("id");String pwd=request.getParam("pwd");
        Student student=schoolSystemService.getStudentInfo(id,pwd);
        studentInfoService.updateInfo(student);
        List<Course> courses=schoolSystemService.getCoursesInfo(id,pwd);
        courseService.updateCourses(qqId,courses);
        return BaseResponse.success(null);
    }

    @Override
    public BaseResponse<Object> unbindStudent(BaseRequest request) {
        String qqId=request.getQqId();
        studentInfoService.deleteInfo(qqId);
        courseService.deleteCourses(qqId);
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
    public BaseResponse<Task> addPublicTask(BaseRequest request) {
        return BaseResponse.success(taskService.addPublicTask(request.getParam("task",Task.class)));
    }

    @Override
    public BaseResponse<Task> deletePublicTask(BaseRequest request) {
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
