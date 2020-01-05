package com.fzubb.dubboservice.impl;

import com.fzubb.ExcepTion.WrongParamException;
import com.fzubb.model.dto.PublicTask;
import com.fzubb.model.vo.HomeDataPublicTaskVo;
import com.fzubb.querycondition.PublicTaskCondition;
import com.fzubb.dubboservice.FZUBBReadService;
import com.fzubb.model.dto.Course;
import com.fzubb.model.dto.Student;
import com.fzubb.model.dto.Task;
import com.fzubb.model.vo.TaskVo;
import com.fzubb.model.request.BaseRequest;
import com.fzubb.model.request.PageRequest;
import com.fzubb.model.response.BaseResponse;
import com.fzubb.model.response.PageResponse;
import com.fzubb.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fzubb.service.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class FZUBBReadServiceImpl implements FZUBBReadService {
    @Autowired
    StudentService studentService;
    @Autowired
    CourseService courseService;
    @Autowired
    TaskService taskService;
    public BaseResponse<Object>  login(BaseRequest request) {
          checkParams(request);
          String qqId=request.getQqId();
          Map<String,Object> data=new HashMap<>();
          Student student= studentService.getInfo(qqId);
          if(Objects.isNull(student) || student.unbind())
              return BaseResponse.unbind();
          //添加学生信息
          data.put("student",student);
          //添加课程信息
          List<Course> courses=courseService.getCourses(qqId);
          data.put("courses",courses);
          //添加任务信息
          List<Task> tasks=taskService.getTasks(qqId);
          data.put("tasks",tasks);
          //添加任务公开信息
          List<PublicTask> publicTasks=taskService.getPublicTasks(qqId);
          data.put("publicTasks", publicTasks);

          return  BaseResponse.success(data);
    }


    @Override
    public PageResponse<HomeDataPublicTaskVo> queryHomeDataPublicTasks(PageRequest request) {
         checkParams(request,"page","num","timeLimit");
         PageResponse<HomeDataPublicTaskVo> response=new PageResponse<>();
         PublicTaskCondition condition=PublicTaskCondition.builder()
                 .courseId(request.getParam("courseId"))
                 .lastCommentId(request.getParam("lastCommentId"))
                 .page(request.getParam("page",Integer.class)).num(request.getParam("num",Integer.class))
                 .timeLimit(request.getParam("timeLimit",Integer.class))
                 .build();
         response.setData(taskService.getHomeDataPublicTaskByCourse(request.getQqId(),condition));
         return  response;
    }

    @Override
    public BaseResponse<TaskVo> queryTaskInfo(PageRequest request) {
        checkParams(request,"taskId");
        String qqId=request.getQqId();Long taskId=request.getParam("taskId",long.class);
        return  BaseResponse.success(taskService.getTaskVo(qqId,taskId));
    }
    private void checkParams(BaseRequest request ,String... params) {
        if(request==null || StringUtil.isEmpty(request.getQqId()))
            throw  new WrongParamException("参数qqId不存在");
        Map<String,Object> map=request.getParams();
        if(map==null)
            return;
        for (String param: params
             ) {
            if(Objects.isNull(map.get(param)))
                throw  new WrongParamException("参数"+param+"不存在");
        }
    }
}
