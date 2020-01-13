package com.fzubb.common.util;

import java.util.Calendar;

/*id生成器*/
public class IDUtil {

    /**根据当前时间生成id,标准格式 年（4位）+月（2位)+日（2位）+小时（2位)+分（2位)+秒（2位)*/
    public  static long  timeId(){
          return Long.parseLong(getTime());
    }
    /**从标准格式截取*/
    public  static  long timeId(int beginIndex,int endIndex){
        return Long.parseLong(getTime(beginIndex,endIndex));
    }
    /**时间标准格式字符串 ：年（4位）+月（2位)+日（2位）+小时（2位)+分（2位)+秒（2位)*/
    private  static  String getTime(){
        return getTime(0, 14);
    }
    private  static  String getTime(int beginIndex,int endIndex){
        StringBuilder sb=new StringBuilder();
        Calendar calendar=Calendar.getInstance();
        String year=apply(calendar.get(Calendar.YEAR),4,'0');
        String month=apply(calendar.get(Calendar.MONTH)+1, 2,'0');
        String day=apply(calendar.get(Calendar.DAY_OF_MONTH), 2, '0');
        String hour=apply(calendar.get(Calendar.HOUR_OF_DAY), 2, '0');
        String minute=apply(calendar.get(Calendar.MINUTE), 2, '0');
        String seconds=apply(calendar.get(Calendar.SECOND), 2, '0');
        return sb.append(year).append(month).append(day).append(hour).append(minute).append(seconds).substring(beginIndex, endIndex);
    }


    /**左补齐整数
     * params:
     * #origin  被补充整数
     * #length:补充后的长度
     * #c:填充字符
     * */
    private  static  String apply(int origin,int length,char c){
         return  String.format("%"+c+length+"d",origin);
     }

    public   static  long timeIdWithParam(String param){
        return Long.parseLong(getTime()+param);
    }
}
