package com.fzubb.querycondition;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

@Data
@Builder
public class PublicTaskCondition {
    /*第几页*/
    Integer page;
    /*每页数量*/
    Integer num;

    /*时间限制*/
    Integer   timeLimit;
    /*课程范围限制*/
    String courseId;

    /*上一条评论id*/
    String lastCommentId;
    @Tolerate
    public PublicTaskCondition() {
    }
}
