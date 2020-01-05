package com.fzubb.mapper;

import com.fzubb.model.dto.Course;

import java.util.List;

public interface CourseMapper {
     List<Course>  getCourses(String qqId);
     int updateSC(String qqId,List<Course> courses);
     int deleteSC(String qqId);
}
