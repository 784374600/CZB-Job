package com.fzubb.common.remote.impl;

import com.fzubb.common.dto.Photo;
import com.fzubb.common.constant.Constant;
import com.fzubb.common.remote.PhotoService;
import com.fzubb.common.util.FileUtil;
import org.apache.dubbo.common.logger.Logger;
import org.apache.dubbo.common.logger.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.MessageFormat;
@Service
public class PhotoServiceImpl implements PhotoService {
    private  static Logger logger= LoggerFactory.getLogger(PhotoServiceImpl.class);
    @Override
    public Photo upload(byte[] photo, String name, String... params) {
        String path= FileUtil.getDefaultFilePathWithParams(params);
        try {
            FileUtil.saveFile(photo, path, name);
        } catch (IOException e) {
            logger.info(MessageFormat.format("用户{0}上传图片出错了",params[0]));
            return null;
        }
        Photo p=new Photo();p.setName(name);p.setPath(path);
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
