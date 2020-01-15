package com.fzubb.user.mapper;


import com.fzubb.user.model.Student;

public interface StudentMapper {
    int update(Student student);
    int delete(String qqId);
    Student get(String qqId);
}
