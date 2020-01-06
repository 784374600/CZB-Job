package com.fzubb.dao.model.dto;

import lombok.Data;

@Data
public class PublicTask{
    String qqId;
    Long taskId;
    String courseId;
    Integer commentNum;
    Integer thumbNum;
    Long createTime;
    Long latestCommentTime;
}
