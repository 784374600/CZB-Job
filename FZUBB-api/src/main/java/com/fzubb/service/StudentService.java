package com.fzubb.service;

import com.fzubb.model.dto.Student;

public interface StudentService{
    Student getInfo(String qqId);
    Student updateInfo(Student student);
    void deleteInfo(String qqId);
}
