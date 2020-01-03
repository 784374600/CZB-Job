package com.fzubb.service.remote.Utils.Tool;

import java.util.Calendar;

public class OrderUtil {
    public  static  String  CreateOrder(){
        StringBuilder sb=new StringBuilder();
        Calendar calendar=Calendar.getInstance();
        String year=String.valueOf(calendar.get(Calendar.YEAR));
        String month=String.valueOf(calendar.get(Calendar.MONTH)+1);
        String day=String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        String hour=String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
        String minute=String.valueOf(calendar.get(Calendar.MINUTE));
        String second=String.valueOf(calendar.get(Calendar.SECOND));
        sb.append(year).append(month).append(day).append(hour).append(minute).append(second);
        return sb.toString();
    }
    public  static  String   CreateUniqueOrder(){
          return  CreateOrder()+String.valueOf((int)((Math.random()*9+1)*1000));
    }

}
