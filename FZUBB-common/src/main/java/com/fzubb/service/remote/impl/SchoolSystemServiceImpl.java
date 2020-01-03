package com.fzubb.service.remote.impl;

import com.fzubb.ExcepTion.HttpException;
import com.fzubb.ExcepTion.SchoolSystemException;
import com.fzubb.ExcepTion.WrongPswException;
import com.fzubb.model.dto.Course;
import com.fzubb.model.dto.Student;
import com.fzubb.service.remote.SchoolSystemService;
import com.fzubb.util.HttpUtil;
import com.fzubb.util.RegexUtil;
import com.fzubb.util.StringUtil;
import org.apache.dubbo.common.logger.Logger;
import org.apache.dubbo.common.logger.LoggerFactory;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SchoolSystemServiceImpl implements SchoolSystemService {
    private  static Logger logger= LoggerFactory.getLogger(SchoolSystemServiceImpl.class);

    /**获取学生信息*/
    @Override
    public Student getStudentInfo(String id, String psw) throws SchoolSystemException {
        Element name,sex,major,grade,place,colleage;
        CloseableHttpClient httpClient=HttpUtil.getHttpClient();
        String tempId = this.getSchoolSystemTempId(httpClient,id,psw);
        String res = null;
        try {
            res = HttpUtil.get(httpClient,"http://59.77.226.35/jcxx/xsxx/StudentInformation.aspx?id=" + tempId, null, null);
        } catch (HttpException e) {
          logger.warn(MessageFormat.format("学号{0} 密码{1}获取个人信息错",id,psw));
          throw  new SchoolSystemException("获取学生个人信息异常");
        } finally {
            try {
                if(httpClient!=null)
                    httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Document document = Jsoup.parse(res);
        name = document.getElementById("ContentPlaceHolder1_LB_xm");
        sex = document.getElementById("ContentPlaceHolder1_LB_xb");
        major = document.getElementById("ContentPlaceHolder1_LB_zymc");
        grade = document.getElementById("ContentPlaceHolder1_LB_nj");
        place = document.getElementById("ContentPlaceHolder1_LB_bh");
        colleage = document.getElementById("ContentPlaceHolder1_LB_xymc");
        return Student.builder().id(id).psw(psw).name(name.text()).sex(sex.text()).major(major.text())
                                          .grade(grade.text()).place(place.text()).colleage(colleage.text())
                                          .build();
    }
    /**获取课程信息*/
    @Override
    public List<Course> getCoursesInfo(String id, String psw) throws SchoolSystemException {
           CloseableHttpClient httpClient=HttpUtil.getHttpClient();
           String  tempId=getSchoolSystemTempId(httpClient,id,psw);
           Map<String, String> headers = new HashMap<>();
           headers.put("Referer", "http://59.77.226.35/default.aspx?id=" + tempId);
           String res=null;
           try {
                  res=HttpUtil.get(httpClient,"http://59.77.226.35/right.aspx?id=" + tempId, headers, null);
           } catch (HttpException e) {
               logger.warn(MessageFormat.format("学号{0} 密码{1}获取课程信息错误：{2}",id,psw));
               throw  new SchoolSystemException("获取学生课程信息异常");
           }finally {
               try {
                   if(httpClient!=null)
                       httpClient.close();
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
           List<Course> courses=null;

           /**此处待实现处理获取课程逻辑*/
           return  courses;
    }

    /**获取教务处临时登录许可证tempId*/
    private  String getSchoolSystemTempId(CloseableHttpClient httpClient,String id, String pwd){
        String input = "muser=" + id + "&passwd=" + pwd + "&x=11&y=23";
        Map<String, String> headers = new HashMap<>();
        headers.put("Referer", "http://jwch.fzu.edu.cn/");
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        String loginRes = null;
        try {
            loginRes = HttpUtil.post(httpClient,"http://59.77.226.32/logincheck.asp", headers, input);
        } catch (HttpException e) {
            logger.warn(MessageFormat.format("学号{0} 密码{1}获取教务处临时Id异常",id,pwd));
            throw  new SchoolSystemException("获取教务处临时Id异常");
        }

        String   tempId =  RegexUtil.find(loginRes, "id=([0-9]+)", 1).get(0);
        if (StringUtil.isEmpty(tempId)) {
            throw new WrongPswException();
        } else {
            return tempId;
        }
    }

}
