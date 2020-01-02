package com.fzubb.dubboservice.impl;

import com.fzubb.condition.PublicTaskCondition;
import com.fzubb.dubboservice.FZUBBReadService;
import com.fzubb.dubboservice.service.CourseService;
import com.fzubb.dubboservice.service.StudentInfoService;
import com.fzubb.dubboservice.service.TaskService;
import com.fzubb.model.dto.Course;
import com.fzubb.model.dto.Student;
import com.fzubb.model.dto.Task;
import com.fzubb.model.dto.TaskVo;
import com.fzubb.model.request.BaseRequest;
import com.fzubb.model.request.PageRequest;
import com.fzubb.model.response.BaseResponse;
import com.fzubb.model.response.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class FZUBBReadServiceImpl implements FZUBBReadService {
    @Autowired
    StudentInfoService studentInfoService;
    @Autowired
    CourseService courseService;
    @Autowired
    TaskService taskService;
    public BaseResponse<Object>  login(BaseRequest request) {
          String qqId=request.getQqId();
          Map<String,Object> data=new HashMap<>();
          Student student=studentInfoService.getInfo(qqId);
          if(Objects.isNull(student) || student.unbind())
              return BaseResponse.unbind();
          data.put("studentInfo",student);
          List<Course> courses=courseService.getCourses(qqId);
          data.put("coursesInfo",courses);
          return  BaseResponse.success(data);
    }

    @Override
    public BaseResponse<Object> queryTasksByCourse(BaseRequest request) {
        String qqId=request.getQqId();
        String courseId=request.getParam("courseId");
        Map<String,Object> data=new HashMap<>();
        List<Task> tasks=taskService.getTasksByCourse(qqId,courseId);
        data.put("tasksInfo",tasks);
        return  BaseResponse.success(data);
    }

    @Override
    public PageResponse<TaskVo> queryPublicTasksByCondition(PageRequest request, PublicTaskCondition condition) {
         PageResponse<TaskVo> response=new PageResponse<>();
         response.setData(taskService.getPublicTasks(request.getQqId(),condition));
         return  response;
    }

    @Override
    public BaseResponse<TaskVo> queryTaskInfo(PageRequest request) {
        String qqId=request.getQqId();String taskId=request.getParam("taskId");
        return  BaseResponse.success(taskService.getTaskInfo(Task.builder().qqId(qqId).taskId(taskId).build()));
    }
}
