package com.fzubb.model.vo;

import com.fzubb.model.dto.Answer;
import com.fzubb.model.dto.Task;
import lombok.Data;

import java.util.List;
@Data
public class TaskVo {
    Task task;
    Answer wrong;
    Answer right;
}
