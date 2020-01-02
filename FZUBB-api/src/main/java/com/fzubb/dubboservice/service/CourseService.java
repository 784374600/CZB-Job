package com.fzubb.dubboservice.service;

import com.fzubb.model.dto.Course;
import com.fzubb.model.request.BaseRequest;

import java.util.List;

public interface CourseService {
     List<Course>  getCourses(String qqId);
     List<Course> updateCourses(String qqId,List<Course> courses);
     int deleteCourses(String qqId);
}
