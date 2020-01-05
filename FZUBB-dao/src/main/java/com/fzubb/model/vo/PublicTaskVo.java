package com.fzubb.model.vo;

import com.fzubb.model.dto.Comment;
import com.fzubb.model.dto.PublicTask;
import com.fzubb.model.dto.Task;
import lombok.Data;

import java.util.List;

@Data
public class PublicTaskVo {
    Task task;
    PublicTask publicTask;
    List<Comment> comments;
}
