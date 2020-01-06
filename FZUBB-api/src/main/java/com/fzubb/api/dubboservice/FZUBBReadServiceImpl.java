package com.fzubb.api.dubboservice;

import com.fzubb.dao.model.dto.Course;
import com.fzubb.dao.model.dto.Task;
import com.fzubb.client.request.BaseRequest;
import com.fzubb.client.request.PageRequest;
import com.fzubb.client.response.PageResponse;
import com.fzubb.dao.model.vo.HomeDataPublicTaskVo;
import com.fzubb.dao.model.vo.PublicTaskVo;
import com.fzubb.dao.querycondition.CommentCondition;
import com.fzubb.dao.querycondition.PublicTaskCondition;
import com.fzubb.api.service.TaskCommunityService;
import com.fzubb.dao.model.dto.PublicTask;
import com.fzubb.client.dubboservice.FZUBBReadService;
import com.fzubb.dao.model.dto.Student;
import com.fzubb.client.response.BaseResponse;
import com.fzubb.api.service.base.CourseService;
import com.fzubb.api.service.base.StudentService;
import com.fzubb.api.service.base.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author caizhibin
 */
@Service
public class FZUBBReadServiceImpl implements FZUBBReadService {
    @Autowired
    StudentService studentService;
    @Autowired
    CourseService courseService;
    @Autowired
    TaskService taskService;
    @Autowired
    TaskCommunityService taskCommunityService;
    @Override
    public BaseResponse<Object>  login(BaseRequest request) {
          checkParams(request);
          String qqId=request.getQqId();
          Map<String,Object> data=new HashMap<>(10);
          Student student= studentService.getInfo(qqId);
          if(Objects.isNull(student) || student.unbind()) {
              return BaseResponse.unbind();
          }
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
                 .lastPublicTaskId(request.getParam("lastPublicTaskId"))
                 .page(request.getParam("page",Integer.class)).num(request.getParam("num",Integer.class))
                 .timeLimit(request.getParam("timeLimit",Integer.class))
                 .build();
         response.setData(taskCommunityService.getHomeData(request.getQqId(),condition));
         return  response;
    }

    @Override
    public BaseResponse<Task> queryTask(PageRequest request) {
        checkParams(request,"taskId");
        String qqId=request.getQqId();Long taskId=request.getParam("taskId",long.class);
        return  BaseResponse.success(taskService.getTask(qqId,taskId));
    }

    @Override
    public BaseResponse<PublicTaskVo> queryPublicTask(BaseRequest request) {
        checkParams(request,"taskId","lastCommentId","num","page");
        String qqId=request.getQqId();long taskId=request.getParam("taskId",long.class);
        CommentCondition condition= CommentCondition.builder()
                .lastCommentId(request.getParam("lastCommentId",long.class))
                .num(request.getParam("num",int.class)).page(request.getParam("page",int.class))
                .build();

        return BaseResponse.success(taskCommunityService.getPublicTaskVo(qqId,taskId,condition));
    }


}
