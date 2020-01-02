package com.fzubb.service.remote;

import com.fzubb.ExcepTion.SchoolSystemException;
import com.fzubb.model.dto.Course;
import com.fzubb.model.dto.Student;

import java.util.List;

public interface SchoolSystemService {
    Student  getStudentInfo(String id,String pwd)throws SchoolSystemException;
    List<Course> getCoursesInfo(String id,String pwd) throws  SchoolSystemException;
}
