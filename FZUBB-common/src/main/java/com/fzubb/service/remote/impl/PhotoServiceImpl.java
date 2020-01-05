package com.fzubb.service.remote.impl;

import com.fzubb.Model.dto.Photo;
import com.fzubb.constant.Constant;
import com.fzubb.service.remote.PhotoService;
import com.fzubb.util.FileUtil;
import org.apache.dubbo.common.logger.Logger;
import org.apache.dubbo.common.logger.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.MessageFormat;
@Service
public class PhotoServiceImpl implements PhotoService {
    private  static Logger logger= LoggerFactory.getLogger(PhotoServiceImpl.class);
    @Override
    public Photo upload(byte[] photo,String qqId,String photoName) {
        String path=FileUtil.getDefaultFilePathWithParams(qqId);
        try {
            FileUtil.saveFile(photo, path, photoName);
        } catch (IOException e) {
            logger.info(MessageFormat.format("用户{0}上传图片出错了",qqId));
            return null;
        }
        Photo p=new Photo();p.setName(photoName);p.setPath(path);
        return  p;
    }

    @Override
    public void delete(String url) {
          Photo photo=getPhoto(url);
          FileUtil.delFile(photo.getPath()+"/"+photo.getName());
    }


    /**解析url*/
    private  Photo getPhoto(String url){
         char[] array=url.toCharArray();
         int p=0;
         for(int i=array.length-1;i>=0;i--) {
             if (array[i] == '/') {
                 p = i;
                 break;
             }
         }
         String path=url.substring(Constant.Image_Pre.length(),p);
         String name=url.substring(p+1);
         return  new Photo(Constant.Image_Pre,path,name);
    }
}
