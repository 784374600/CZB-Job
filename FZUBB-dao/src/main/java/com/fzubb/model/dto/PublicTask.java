package com.fzubb.model.dto;

import lombok.Data;

@Data
public class PublicTask{
    String qqId;
    long taskId;
    int commentNum;
    int thumbNum;
    String createTime;
    String newCommentTime;
}
