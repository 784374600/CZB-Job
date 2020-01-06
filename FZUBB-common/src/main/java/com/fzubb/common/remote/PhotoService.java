package com.fzubb.common.remote;

import com.fzubb.common.dto.Photo;

public interface PhotoService {
     String  fail="-1";
     String success="0";
     /**当返回null时，表示上传图片失败*/
     Photo upload(byte[] photo,String name,String... params);
     void  delete(String uri);
}
