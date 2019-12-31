package com.fzubb.dubboservice.service;

import org.springframework.web.multipart.MultipartFile;

public interface PhotoService {
     String  fail="-1";
     String success="0";
     String  upload(MultipartFile photo);
     String  delete(String uri);
}
