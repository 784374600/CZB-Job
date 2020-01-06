package com.fzubb.dao.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

@Data@Builder
public class Comment {
    long taskId;
    String qqId;
    long commentId;
    String content;
    @Tolerate
    public Comment() {
    }
}
