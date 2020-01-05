package com.fzubb.service.remote;

import com.fzubb.Model.dto.Photo;

import java.io.File;

public interface PhotoService {
     String  fail="-1";
     String success="0";
     /**当返回null时，表示上传图片失败*/
     Photo upload(byte[] photo,String qqId,String photoName);
     void  delete(String uri);
}
