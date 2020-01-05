package com.fzubb.mapper;

import com.fzubb.model.dto.Comment;
import com.fzubb.model.dto.PublicTask;
import com.fzubb.model.dto.Task;
import com.fzubb.model.vo.PublicTaskVo;
import com.fzubb.model.vo.TaskVo;

import java.util.List;

public interface TaskMapper {
        int addTask(Task task);
        int deleteTask(Task task);
        TaskVo getTask(String qqId,long taskId);
        List<Task> getTasksByCourse(String qqId,String courseId);

        int addPublicTask(Task task);
        int deletePublicTask(Task task);
        int updatePublicTask(Task task);
        List<PublicTask> getPublishTask(String qqId);
        PublicTaskVo getPublicTaskVo(PublicTask publicTask);


        int addComment(Comment comment);
        int deleteComment(Comment comment);
        List<Comment> getComments(String qqId,String )


}
