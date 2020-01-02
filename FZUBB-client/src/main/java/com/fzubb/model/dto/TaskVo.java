package com.fzubb.model.dto;

import lombok.Data;

import java.util.List;
@Data
public class TaskVo {
    Task task;
    List<Comment> comments;
}
