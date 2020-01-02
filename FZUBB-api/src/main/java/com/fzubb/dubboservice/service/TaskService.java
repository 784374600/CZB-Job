package com.fzubb.dubboservice.service;

import com.fzubb.condition.PublicTaskCondition;
import com.fzubb.model.dto.Comment;
import com.fzubb.model.dto.Task;
import com.fzubb.model.dto.TaskVo;

import java.util.List;

public interface TaskService {
    List<Task>  getTasksByCourse(String qqId,String courseId);
    Task addTask(Task task);
    Task deleteTask(Task task);
    TaskVo getTaskInfo(Task task);
    TaskVo getPublicTasks(String qqId, PublicTaskCondition condition);
    Task addPublicTask(Task task);
    Task deletePublicTask(Task task);
    Comment addComment(Comment comment);
    Comment deleteComment(Comment comment);
}
