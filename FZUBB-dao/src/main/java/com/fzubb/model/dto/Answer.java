package com.fzubb.model.dto;

import lombok.Data;

@Data
public class Answer {
    String qqId;
    long taskId;
    String note;
    String[] content;
    String[] answer;
}
