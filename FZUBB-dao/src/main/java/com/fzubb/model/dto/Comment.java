package com.fzubb.model.dto;

import lombok.Data;

@Data
public class Comment {
    String taskId;
    String qqId;
    String content;
    String createTime;
}
