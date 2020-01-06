package com.fzubb.api.service.base;

import com.fzubb.dao.model.dto.Course;

import java.util.List;

public interface CourseService {
     List<Course>  getCourses(String qqId);
     List<Course> updateCourses(List<Course> courses);
     void deleteSC(String qqId);
     void updateSC(String qqId,List<Course> courses);
}
