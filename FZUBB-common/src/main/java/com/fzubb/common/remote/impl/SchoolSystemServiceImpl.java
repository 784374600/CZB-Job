package com.fzubb.common.remote.impl;

import com.fzubb.common.ExcepTion.HttpException;
import com.fzubb.common.ExcepTion.SchoolSystemException;
import com.fzubb.common.ExcepTion.WrongPswException;
import com.fzubb.dao.model.dto.Course;
import com.fzubb.dao.model.dto.Student;
import com.fzubb.common.remote.SchoolSystemService;
import com.fzubb.common.util.HttpUtil;
import com.fzubb.common.util.RegexUtil;
import com.fzubb.common.util.StringUtil;
import org.apache.dubbo.common.logger.Logger;
import org.apache.dubbo.common.logger.LoggerFactory;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.*;
@Service
public class SchoolSystemServiceImpl implements SchoolSystemService {
    private  static Logger logger= LoggerFactory.getLogger(SchoolSystemServiceImpl.class);

    /**获取学生信息*/
    @Override
    public Student getStudentInfo(String id, String psw) throws SchoolSystemException {

        /*获取学生信息html*/
        CloseableHttpClient httpClient= HttpUtil.getHttpClient();
        String tempId = this.getSchoolSystemTempId(httpClient,id,psw);
        String res = null;
        try {
            res = HttpUtil.get(httpClient,"http://59.77.226.35/jcxx/xsxx/StudentInformation.aspx?id=" + tempId, null, null);
        } catch (HttpException e) {
          logger.warn(MessageFormat.format("学号{0} 密码{1}获取个人信息错",id,psw));
          throw  new SchoolSystemException("获取学生个人信息异常");
        } finally {
            try {
                if(httpClient!=null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /*分析教务处学生信息html*/
        Document document = Jsoup.parse(res);
        Element name,sex,major,grade,place,colleage;
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
        /*获取课程表html*/
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
                   if(httpClient!=null) {
                       httpClient.close();
                   }
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }

        /*分析课程表html*/
            Map<String,Course> courseMap=new HashMap<>();
            Document document = Jsoup.parse(res);
            Element table = document.getElementById("LB_kb").getElementsByTag("table").first();
            Elements trs=table.getElementsByTag("tr");
            int i=-1;
            for (Element tr: trs) {
                i++;
                if(i==0 || i==12) {
                    continue;
                }
                Elements tds=tr.getElementsByTag("td");
                int offset=0;
                if(i==1 || i==5 || i==9) {
                    offset = 1;
                }
                int j=-1;
                for (Element td: tds) {
                    j++;
                    String text=td.text();
                    if(text.length()>20){
                        //得到CoursesIds
                        List<String> courseIds= RegexUtil.find(td.html(), "kcdm=([0-9]*)&", 1);
                        List<Course> courses=decodes(td.text());
                        for (int k=0;k<courses.size();k++) {
                            Course course=courses.get(k);
                            Course.Teach teach=course.getTeachList().get(0);
                            teach.setDay(j-offset);
                            teach.setTime1(i);
                            teach.setTime2(i);
                            course.setCourseId(courseIds.get(k));

                            Course orign=courseMap.get(course.getCourseId());
                            if(orign==null) {
                                courseMap.put(course.getCourseId(), course);
                            }else {
                                orign.getTeachList().addAll(course.getTeachList());
                            }
                        }

                    }

                }

            }

        /*至此，得到了所有Course,对所有的Course的List<Course.Teach> 整合排序*/
            List<Course> courses=new ArrayList<>();
            for (Map.Entry<String,Course> entry:courseMap.entrySet()
                 ) {
                Course course=entry.getValue();
                sortTeachList(course.getTeachList());
                courses.add(course);
            }

        return  courses;
    }

    /**获取教务处临时登录许可证tempId*/
    private  String getSchoolSystemTempId(CloseableHttpClient httpClient,String id, String pwd){
        String input = "muser=" + id + "&passwd=" + pwd + "&x=11&y=23";
        Map<String, String> headers = new HashMap<>();
        headers.put("Referer", "http://jwch.fzu.edu.cn/");
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        String loginRes,tempId;
        try {
            loginRes = HttpUtil.post(httpClient,"http://59.77.226.32/logincheck.asp", headers, input);
            tempId=RegexUtil.find(loginRes, "id=([0-9]+)", 1).get(0);
        } catch (HttpException e) {
            logger.warn(MessageFormat.format("学号{0} 密码{1}获取教务处临时Id异常",id,pwd));
            throw  new SchoolSystemException("获取教务处临时Id异常");
        }catch (Exception e) {
          throw new WrongPswException();
        }
        if (StringUtil.isEmpty(tempId)) {
            throw new WrongPswException();
        } else {
            return tempId;
        }
    }
    /**分析html得到每天的每一节课*/
    private  List<Course> decodes(String text){
        List<Course> courses=new ArrayList<>();
        String[] words=text.split(" ");
        int l=words.length;boolean[] f=new boolean[4];
        Course course=new Course();
        Course.Teach teach=new Course.Teach();
        for(int i=l-1;i>=0;i--){
            if(StringUtil.isEmpty(words[i])) {
                continue;
            }

            if(!f[0]){
                String[] week=words[i].split("-");
                course.setWeek1(Integer.parseInt(week[0]));
                course.setWeek2(Integer.parseInt(week[1]));
                f[0]=true;
            }else  if("[单]".equals(words[i]) || "[双]".equals(words[i])){
                if("[单]".equals(words[i])) {
                    course.setType(1);
                } else {
                    course.setType(2);
                }
                f[1]=true;f[2]=true;
            }
            else if("[教学大纲|授课计划]".equals(words[i])){
                f[3]=true;f[1]=true;f[2]=true;
            }else  if(!f[1] || !f[2]){
                if(!words[i].startsWith("[")) {
                    course.setTeacher(words[i]);
                    f[1]=true;
                }else {
                    teach.setPlace(words[i].substring(1,words[i].length()-1));
                    f[2]=true;
                }
            }else  if(f[3]){
                course.setName(words[i]);
                courses.add(course);
                course.setTeachList(new ArrayList<>());
                course.getTeachList().add(teach);
                course=new Course();teach=new Course.Teach();
                f[0]=false;f[1]=false;f[2]=false;f[3]=false;
            }

        }
        return  courses;
    }
    /**对TeachList排序整合*/
    private void sortTeachList(List<Course.Teach> teacheList){
        teacheList.sort((o1,o2)->{
            if(o1.getDay()-o2.getDay()!=0) {
                return o1.getDay() - o2.getDay();
            }

            return  o1.getTime1()-o2.getTime1();
        });
        Course.Teach pre=null;
        List<Course.Teach> list=new ArrayList<>();
        for(int i=0;i<teacheList.size();i++){
           Course.Teach cur=teacheList.get(i);
           if(pre!=null &&cur.getDay()==pre.getDay()&& cur.getTime1()==pre.getTime1()+1) {
               pre.setTime2(cur.getTime1());
           } else {
                pre = cur;
                list.add(cur);
            }
        }
        teacheList.clear();
        teacheList.addAll(list);
    }

}
