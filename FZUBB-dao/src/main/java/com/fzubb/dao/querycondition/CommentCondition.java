package com.fzubb.dao.querycondition;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

@Data@Builder
public class CommentCondition {
    /*第几页*/
    int page;
    /*每页数量*/
    int num;

    /*上一条评论*/
    long lastCommentId;
    @Tolerate
    public CommentCondition() {
    }
}
