package com.fzubb.dao.mapper;

import com.fzubb.dao.model.dto.Comment;
import com.fzubb.dao.model.dto.PublicTask;
import com.fzubb.dao.model.dto.PublicTaskId;
import com.fzubb.dao.model.dto.Task;
import com.fzubb.dao.model.dto.*;
import com.fzubb.dao.querycondition.CommentCondition;
import com.fzubb.dao.querycondition.PublicTaskCondition;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TaskMapper {
        int addTask(Task task);
        int deleteTask(String qqId,long taskId);
        int updateTask(Task task);
        List<Task> getTasks(String qqId);
        Task getTask(String qqId,long taskId);

        int addPublicTask(@Param("task") PublicTask task);
        int deletePublicTask(String qqId,long taskId);
        int updatePublicTask(@Param("task")PublicTask task);
        PublicTask getPublicTask(String qqId,long taskId);

        List<PublicTaskId> getPublicTaskIdsByCourse(@Param("today") long today,@Param("condition") PublicTaskCondition condition);
        List<PublicTask> getOwnPublicTasks(String qqId);


        int addComment(Comment comment);
        int deleteComment(String qqId,long taskId,long commentId);
        List<Comment> getComments(@Param("qqId") String qqId, @Param("taskId") long taskId,@Param("condition") CommentCondition condition);


}
