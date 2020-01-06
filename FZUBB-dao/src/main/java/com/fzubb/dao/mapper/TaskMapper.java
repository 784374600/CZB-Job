package com.fzubb.dao.mapper;

import com.fzubb.dao.model.dto.Comment;
import com.fzubb.dao.model.dto.PublicTask;
import com.fzubb.dao.model.dto.PublicTaskId;
import com.fzubb.dao.model.dto.Task;
import com.fzubb.dao.model.dto.*;
import com.fzubb.dao.querycondition.CommentCondition;
import com.fzubb.dao.querycondition.PublicTaskCondition;

import java.util.List;

public interface TaskMapper {
        int addTask(Task task);
        int deleteTask(String qqId,long taskId);
        int updateTask(Task task);
        List<Task> getTasks(String qqId);
        Task getTask(String qqId,long taskId);
        List<Task> getTasksByCourse(String qqId,String courseId);

        int addPublicTask(PublicTask task);
        int deletePublicTask(String qqId,long taskId);
        int updatePublicTask(Task task);
        PublicTask getPublicTask(String qqId,long taskId);
        List<PublicTaskId> getPublicTaskIdsByCourse(PublicTaskCondition condition);
        List<PublicTask> getOwnPublicTasks(String qqId);


        int addComment(Comment comment);
        int deleteComment(String qqId,long taskId,long commentId);
        List<Comment> getComments(String qqId, long taskId, CommentCondition condition);


}
