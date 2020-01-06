package com.fzubb.api.service.impl;

import com.fzubb.dao.model.vo.HomeDataPublicTaskVo;
import com.fzubb.dao.model.vo.PublicTaskVo;
import com.fzubb.dao.querycondition.CommentCondition;
import com.fzubb.dao.querycondition.PublicTaskCondition;
import com.fzubb.api.service.TaskCommunityService;
import com.fzubb.dao.model.dto.PublicTask;
import com.fzubb.api.service.base.StudentService;
import com.fzubb.api.service.base.TaskService;
import org.apache.dubbo.common.utils.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class TaskCommunityServiceImpl  implements TaskCommunityService {
    @Autowired
    TaskService taskService;
    @Autowired
    StudentService studentService;

    @Override
    public List<HomeDataPublicTaskVo> getHomeData(String courseId, PublicTaskCondition condition) {

        List<PublicTask>   publicTasks=taskService.getPublicTasksByCondition(condition);
        if(CollectionUtils.isEmpty(publicTasks)) {
            return null;
        }
        List<HomeDataPublicTaskVo> homeDataPublicTaskVos=new ArrayList<>();
        for (PublicTask publicTask : publicTasks
        ) {
            HomeDataPublicTaskVo vo=new HomeDataPublicTaskVo();
            vo.setTask(taskService.getTask(publicTask.getQqId(),publicTask.getTaskId()));
            vo.setPublicTask(publicTask);
            vo.setStudent(studentService.getInfo(publicTask.getQqId()));
            homeDataPublicTaskVos.add(vo);
        }
        return  homeDataPublicTaskVos;
    }

    @Override
    public PublicTaskVo getPublicTaskVo(String qqId, long taskId, CommentCondition commentCondition) {
        PublicTaskVo publicTaskVo=new PublicTaskVo();
        /*当首次点入详情页，添加任务详情，添加点赞，评论数等信息*/
        if(commentCondition.getPage()==0 || commentCondition.getLastCommentId()==0){
            publicTaskVo.setTask(taskService.getTask(qqId,taskId));
            publicTaskVo.setStudent(studentService.getInfo(qqId));
            publicTaskVo.setPublicTask(taskService.getPublicTask(qqId,taskId));
        }
        publicTaskVo.setComments(taskService.getComments(qqId,taskId,commentCondition));
        return publicTaskVo;
    }

}
