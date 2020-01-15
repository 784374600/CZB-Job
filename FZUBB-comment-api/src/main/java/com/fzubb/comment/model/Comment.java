package com.fzubb.comment.model;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

@Data
@Builder
public class Comment {
    String business;
    long taskId;
    String qqId;
    long commentId;
    String content;
    @Tolerate
    public Comment() {
    }
}
