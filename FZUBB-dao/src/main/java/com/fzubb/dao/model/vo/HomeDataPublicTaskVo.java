package com.fzubb.dao.model.vo;

import com.fzubb.dao.model.dto.Task;
import com.fzubb.dao.model.dto.PublicTask;
import com.fzubb.dao.model.dto.Student;
import lombok.Data;

@Data
public class HomeDataPublicTaskVo {
    Student student;
    Task task;
    PublicTask publicTask;

}
