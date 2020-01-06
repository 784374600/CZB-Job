package com.fzubb.dao.mapper;

import com.fzubb.dao.model.dto.Student;

public interface StudentMapper {
    int update(Student student);
    int delete(String qqId);
    Student get(String qqId);
}
