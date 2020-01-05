package com.fzubb.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

@Data
@Builder
public class Task {
    long taskId;
    String qqId;
    String courseId;
    String theme;
    String note;
    String endTime;
    boolean isFinish;
    String[] content;
    String[] photos;
    @Tolerate
    public Task() {
    }
}
