package com.fzubb.service;

import com.fzubb.model.dto.PublicTaskId;
import com.fzubb.model.vo.HomeDataPublicTaskVo;
import com.fzubb.querycondition.CommentCondition;
import com.fzubb.model.dto.Comment;
import com.fzubb.model.dto.PublicTask;
import com.fzubb.model.dto.Task;
import com.fzubb.model.vo.PublicTaskVo;
import com.fzubb.model.vo.TaskVo;
import com.fzubb.querycondition.PublicTaskCondition;

import java.util.List;

public interface TaskService {
    List<Task>  getTasks(String qqId);
    Task addTask(Task task);
    Task deleteTask(Task task);
    TaskVo getTaskVo(String qqId,long taskId);

    List<PublicTask> getPublicTasks(String qqId);//获取个人有哪些任务公开
    PublicTask addPublicTask(Task task);
    PublicTask deletePublicTask(Task task);
    PublicTaskVo getPublicTaskVo(PublicTask publicTask, CommentCondition condition);
    List<HomeDataPublicTaskVo>  getHomeDataPublicTaskByCourse(String courseId, PublicTaskCondition condition);
    PublicTask getPublicTask(String qqId,long taskId);

    Comment addComment(Comment comment);
    Comment deleteComment(Comment comment);
}
