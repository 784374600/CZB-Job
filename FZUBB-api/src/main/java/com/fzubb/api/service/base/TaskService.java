package com.fzubb.api.service.base;

import com.fzubb.dao.model.dto.Comment;
import com.fzubb.dao.model.dto.PublicTask;
import com.fzubb.dao.model.dto.Task;
import com.fzubb.dao.querycondition.CommentCondition;
import com.fzubb.dao.querycondition.PublicTaskCondition;

import java.util.List;

public interface TaskService {
    List<Task>  getTasks(String qqId);
    Task addTask(Task task);
    Task deleteTask(String qqId,long taskId);
    Task getTask(String qqId,long taskId);

    List<PublicTask> getPublicTasks(String qqId);//获取个人有哪些任务公开

    PublicTask addPublicTask(PublicTask task);
    PublicTask deletePublicTask(String qqId,long taskId);
    PublicTask getPublicTask(String qqId,long taskId);
    List<PublicTask>  getPublicTasksByCondition(PublicTaskCondition publicTaskCondition);




    Comment addComment(Comment comment);
    Comment deleteComment(String qqId,long taskId,long commentId);
    List<Comment> getComments(String qqId, long taskId, CommentCondition condition);
}
