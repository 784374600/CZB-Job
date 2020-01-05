package com.fzubb.querycondition;

import lombok.Data;

@Data
public class CommentCondition {
    /*第几页*/
    int page;
    /*每页数量*/
    int num;

    /*上一条评论id*/
    String lastCommentId;
}
