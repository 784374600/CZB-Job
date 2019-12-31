package com.fzubb.dubboservice.impl;

import com.fzubb.dubboservice.FZUBBReadService;
import com.fzubb.dubboservice.service.StudentInfoService;
import com.fzubb.model.dto.Student;
import com.fzubb.model.request.BaseRequest;
import com.fzubb.model.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
@Service
public class FZUBBReadServiceImpl implements FZUBBReadService {
    @Autowired
    StudentInfoService studentInfoService;
    public BaseResponse<Object> login(BaseRequest request) {
          String qqId=request.getQqId();
          if(StringUtils.isEmpty(qqId))
              return  BaseResponse.param_error("qqId");
          Map<String,Object> data=new HashMap<>();
          Student student=studentInfoService.getInfo(qqId);
          data.put("studentInfo",student);
          return  BaseResponse.success(data);
    }

    public BaseResponse<Object> queryTasksByCourse(BaseRequest request) {


        return null;
    }
}
