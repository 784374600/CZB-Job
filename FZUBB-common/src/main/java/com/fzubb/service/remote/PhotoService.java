package com.fzubb.service.remote;

import com.fzubb.Model.Photo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface PhotoService {
     String  fail="-1";
     String success="0";
     /**当返回null时，表示上传图片失败*/
     Photo upload(MultipartFile photo);
     String  delete(String uri);
}
