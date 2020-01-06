package com.fzubb.common.remote;

import com.fzubb.common.ExcepTion.SchoolSystemException;
import com.fzubb.dao.model.dto.Course;
import com.fzubb.dao.model.dto.Student;

import java.util.List;

public interface SchoolSystemService {
    Student  getStudentInfo(String id,String pwd)throws SchoolSystemException;
    List<Course> getCoursesInfo(String id,String pwd) throws  SchoolSystemException;
}
