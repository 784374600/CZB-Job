package com.fzubb.mapper;

import com.fzubb.model.dto.Student;

public interface StudentMapper {
    int update(Student student);
    int delete(String qqId);
    Student get(String qqId);
}
