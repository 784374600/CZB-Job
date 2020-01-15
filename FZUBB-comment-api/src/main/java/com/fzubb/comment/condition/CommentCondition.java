package com.fzubb.comment.condition;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

@Data
@Builder
public class CommentCondition {
    String bussiness;
    String qqId;
    Long  taskId;
    Long lastCommentId;
    Integer page;
    Integer num;
    @Tolerate
    public CommentCondition() {
    }
}
