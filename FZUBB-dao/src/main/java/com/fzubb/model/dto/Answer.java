package com.fzubb.model.dto;

import lombok.Data;

@Data
public class Answer {
    String qqId;
    Long taskId;
    String note;
    String[] content;
    String[] answer;
}
