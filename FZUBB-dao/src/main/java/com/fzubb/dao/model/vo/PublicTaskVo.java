package com.fzubb.dao.model.vo;

import com.fzubb.dao.model.dto.Task;
import com.fzubb.dao.model.dto.Comment;
import com.fzubb.dao.model.dto.PublicTask;
import com.fzubb.dao.model.dto.Student;
import lombok.Data;

import java.util.List;

@Data
public class PublicTaskVo {
    Student student;
    Task task;
    PublicTask publicTask;
    List<Comment> comments;
}
