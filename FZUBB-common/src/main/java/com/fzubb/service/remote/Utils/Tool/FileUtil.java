package com.fzubb.service.remote.Utils.Tool;


import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class FileUtil {
    public static String saveImg(MultipartFile multipartFile, String path) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        FileInputStream fileInputStream = (FileInputStream) multipartFile.getInputStream();
        String fileName = UUID.randomUUID().toString() + ".png";
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(path + File.separator + fileName));
        byte[] bs = new byte[1024];
        int len;
        while ((len = fileInputStream.read(bs)) != -1) {
            bos.write(bs, 0, len);
        }
        bos.flush();
        bos.close();
        return fileName;
    }
    public  static  String getFilePath(String openId){
        StringBuilder sb=new StringBuilder();
        Calendar calendar=Calendar.getInstance();
        String year=String.valueOf(calendar.get(Calendar.YEAR));
        String month=String.valueOf(calendar.get(Calendar.MONTH)+1);
        String day=String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        sb.append("/").append(year).append("/").append(month).append("/").append(day).append("/").append(openId);
        return sb.toString();
    }

   public static boolean delFile(File file) {
        if (!file.exists()) {
            return false;
        }

        if (file.isDirectory()) {
        File[] files = file.listFiles();
        for (File f : files) {
            delFile(f);
        }
    }
        return file.delete();
   }
   public  static List<String> saveImgs(MultipartFile[] imgs , String path){
        if(ObjectUtil.Empty(imgs))
            return  null;
        List<String> paths=new ArrayList<>();
        boolean isSuccess=true;
       try {
           for (int i=0;i<imgs.length;i++){
                paths.add(path+"/"+saveImg(imgs[i],path));
           }
           return  paths;
       } catch (IOException e) {
           e.printStackTrace();
           isSuccess=false;
           return  null;
       }finally {
            if(!isSuccess){
                 for (int i=0;i<paths.size();i++)
                     delFile(new File(paths.get(i)));
            }
       }
   }



}
