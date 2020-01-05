package com.fzubb.querycondition;

import lombok.Data;

@Data
public class PublicTaskCondition {
    /*第几页*/
    int page;
    /*每页数量*/
    int num;

    /*时间限制*/
    int   timeLimit;
    /*课程范围限制*/
    String courseId;

    /*上一条评论id*/
    String lastCommentId;
}
