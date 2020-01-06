package com.fzubb.dao.model.vo;

import com.fzubb.dao.model.dto.Task;
import com.fzubb.dao.model.dto.Answer;
import lombok.Data;

@Data
public class TaskVo {
    Task task;
    Answer wrong;
    Answer right;
}
