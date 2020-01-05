package com.fzubb.model.dto;

import lombok.Data;

@Data
public class Comment {
    long taskId;
    String qqId;
    long commentId;
    String content;
    String createTime;
}
