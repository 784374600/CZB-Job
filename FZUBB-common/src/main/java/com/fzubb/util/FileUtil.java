package com.fzubb.util;


import com.fzubb.service.remote.Utils.Tool.ObjectUtil;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class FileUtil {
    public static void saveFile(byte[] file, String filePath,String fileName) throws IOException {
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath +"/"+ fileName);
        out.write(file);
        out.flush();
        out.close();
    }
    /**生成默认格式路径，可以添加参数* 格式 年份/月份/日/param1/param2... */
    public  static  String getDefaultFilePathWithParams(String... params){
        StringBuilder sb=new StringBuilder();
        Calendar calendar=Calendar.getInstance();
        String year=String.valueOf(calendar.get(Calendar.YEAR));
        String month=String.valueOf(calendar.get(Calendar.MONTH)+1);
        String day=String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        sb.append("/").append(year).append("/").append(month).append("/").append(day);
        if(params!=null && params.length>0){
             for(int i=0;i<params.length;i++)
                 sb.append("/").append(params[i]);
        }
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
   public  static  boolean delFile(String filePath){
        return  delFile(new File(filePath));
   }



}
