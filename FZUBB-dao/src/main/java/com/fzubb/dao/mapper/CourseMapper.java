package com.fzubb.dao.mapper;

import com.fzubb.dao.model.dto.Course;

import java.util.List;

public interface CourseMapper {
     List<String>  getCourses(String qqId);
     int updateSC(String qqId,List<Course> courses);
     int deleteSC(String qqId);
}
