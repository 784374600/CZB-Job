package com.fzubb.dubboservice.service;

import com.fzubb.model.dto.Student;
import com.fzubb.model.request.BaseRequest;

import java.util.Map;

public interface StudentInfoService {
      Student  getInfo(String qqId);
      Student updateInfo(Student student);
      int deleteInfo(String qqId);
}
