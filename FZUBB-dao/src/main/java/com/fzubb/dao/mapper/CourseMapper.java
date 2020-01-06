package com.fzubb.dao.mapper;

import com.fzubb.dao.model.dto.Course;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CourseMapper {
     List<String>  getCourses(String qqId);
     int updateSC(@Param("qqId") String qqId,@Param("courses") List<Course> courses);
     int deleteSC(String qqId);
}
