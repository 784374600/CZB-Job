package com.fzubb.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Task {
    String taskId;
    String qqId;
    String courseId;
    String theme;
    String note;
    String endTime;
    boolean isFinish;
    String[] content;
    String[] photos;
}
