package com.fzubb.api.service.base;

import com.fzubb.dao.model.dto.Student;

public interface StudentService{
    Student getInfo(String qqId);
    Student updateInfo(Student student);
    void deleteInfo(String qqId);
}
