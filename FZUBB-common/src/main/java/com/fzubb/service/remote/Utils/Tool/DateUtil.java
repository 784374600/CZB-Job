package com.fzubb.service.remote.Utils.Tool;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    public static long  getDistanceOfTwoDay(String beginDate ,String endDate){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        //跨年不会出现问题
        //如果时间为：2016-03-18 11:59:59 和 2016-03-19 00:00:01的话差值为 0
        Date fDate= null;
        Date oDate= null;
        try {
            fDate = sdf.parse(beginDate);
            oDate = sdf.parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long days=(oDate.getTime()-fDate.getTime())/(1000*3600*24);
        return  days;
    }
    public  static  String getCurDate(){
        StringBuilder sb=new StringBuilder();
        Calendar calendar=Calendar.getInstance();
        String year=String.valueOf(calendar.get(Calendar.YEAR));
        String month=String.valueOf(calendar.get(Calendar.MONTH)+1);
        String day=String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        sb.append(year).append("-").append(month).append("-").append(day);
        return  sb.toString();
    }
    public  static  String getCurTime(){
        StringBuilder sb=new StringBuilder();
        Calendar calendar=Calendar.getInstance();
        String year=String.valueOf(calendar.get(Calendar.YEAR));
        String month=String.valueOf(calendar.get(Calendar.MONTH)+1);
        String day=String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        String hour=String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
        String minute=String.valueOf(calendar.get(Calendar.MINUTE));
        String seconds=String.valueOf(calendar.get(Calendar.SECOND));
        sb.append(year).append("-").append(month).append("-").append(day).append(" ").append(hour).append(":")
        .append(minute).append(":").append(seconds);
        return  sb.toString();
    }
    public  static  String getCurDateWithHour(){
        StringBuilder sb=new StringBuilder();
        Calendar calendar=Calendar.getInstance();
        String year=String.valueOf(calendar.get(Calendar.YEAR));
        String month=String.valueOf(calendar.get(Calendar.MONTH)+1);
        String day=String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        String hour=String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
        sb.append(year).append("-").append(month).append("-").append(day).append(" ").append(hour);
        return  sb.toString();
    }
    public static int dayForWeek(String pTime) throws Throwable {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date tmpDate = format.parse(pTime);
        Calendar cal = Calendar.getInstance();
        int[] weekDays = { 7, 1, 2, 3, 4, 5, 6 };
        try {
            cal.setTime(tmpDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    public static String calFullDate(Date date, int yearNum, int monthNum, int dateNum,int hourNum) {
        String result = "";
        try {
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.MONTH, monthNum);
            cal.add(Calendar.YEAR, yearNum);
            cal.add(Calendar.DATE, dateNum);
            cal.add(Calendar.HOUR, hourNum);
            result = sd.format(cal.getTime());
        } catch (Exception e) {
           e.printStackTrace();
        }
        return result;

    }

}
