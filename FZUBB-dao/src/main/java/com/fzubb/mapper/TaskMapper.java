package com.fzubb.mapper;

import com.fzubb.model.dto.Comment;
import com.fzubb.model.dto.PublicTask;
import com.fzubb.model.dto.PublicTaskId;
import com.fzubb.model.dto.Task;
import com.fzubb.model.vo.PublicTaskVo;
import com.fzubb.model.vo.TaskVo;
import com.fzubb.querycondition.CommentCondition;
import com.fzubb.querycondition.PublicTaskCondition;

import java.util.List;

public interface TaskMapper {
        int addTask(Task task);
        int deleteTask(Task task);
        List<Task> getTasks(String qqId);
        TaskVo getTaskVo(String qqId,long taskId);
        List<Task> getTasksByCourse(String qqId,String courseId);

        int addPublicTask(Task task);
        int deletePublicTask(Task task);
        int updatePublicTask(Task task);
        PublicTask getPublicTask(String qqId,long taskId);
        PublicTaskVo getPublicTaskVo(PublicTask publicTask);
        List<PublicTaskId> getPublicTaskIdsByCourse(String courseId, PublicTaskCondition condition);
        List<PublicTask> getOwnPublicTasks(String qqId);


        int addComment(Comment comment);
        int deleteComment(Comment comment);
        List<Comment> getComments(String qqId, long taskId, CommentCondition condition);


}
