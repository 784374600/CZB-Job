package com.fzubb.service;

import com.fzubb.querycondition.CommentCondition;
import com.fzubb.model.dto.Comment;
import com.fzubb.model.dto.PublicTask;
import com.fzubb.model.dto.Task;
import com.fzubb.model.vo.PublicTaskVo;
import com.fzubb.model.vo.TaskVo;

import java.util.List;

public interface TaskService {
    List<Task>  getTasksByCourse(String qqId,String courseId);
    Task addTask(Task task);
    Task deleteTask(Task task);
    TaskVo getTaskVo(Task task);

    List<PublicTask> getPublicTasks(String qqId);//获取个人有哪些任务公开
    PublicTask addPublicTask(Task task);
    PublicTask deletePublicTask(Task task);
    PublicTaskVo getPublicTaskVo(PublicTask publicTask, CommentCondition condition);
    List<PublicTask>  getPublicTaskByCourse(String courseId);

    Comment addComment(Comment comment);
    Comment deleteComment(Comment comment);
}
