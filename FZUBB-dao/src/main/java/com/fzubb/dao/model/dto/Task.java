package com.fzubb.dao.model.dto;

import com.fzubb.dao.constant.PublicType;
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
    Long endTime;
    Boolean isFinish;
    String[] content;
    String[] photos;
    Answer  wrong;
    Answer  right;
    Integer publicType= PublicType.notShowAnswers;
    @Tolerate
    public Task() {
    }
}
