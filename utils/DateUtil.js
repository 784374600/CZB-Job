//将日期格式如2018/02/03 或20180203转换为2018-02-03
const transferDate=(date)=>{
    let year,month,day;
    year=date.substr(0,4);
    if(date.length===8){
        month=date.substr(4,2);
        day=date.substr(6,2);
    }else{
        month=date.substr(5,2);
        day=date.substr(8,2);
    }
    return year+'-'+month+'-'+day;
}
//将时间格式如180203转换为18：02：03
const transferTime=(time)=>{
    let hour,minute,seconds;
    hour=time.substr(0,2);
    minute=time.substr(2,2);
    seconds=time.substr(4,2);
    return hour+':'+minute+':'+seconds;

}
//将时间格式如18:02:03转换为180203
const reverseTime=(time)=>{
    let hour,minute,seconds;
    hour=time.substr(0,2);
    minute=time.substr(3,2);
    seconds=time.substr(6,2);
    return hour+minute+seconds;
}
//将日期格式如2018-02-03 转换为20180203
const reverseDate=(date)=>{
    let year,month,day;
    year=date.substr(0,4);
    month=date.substr(5,2);
    day=date.substr(8,2);

    return year+month+day;
}
//计算天数差的函数，通用
function DateDiff(sDate1, sDate2) { //sDate1和sDate2是2019-03-12 或20190312 或2019/03/12格式
    sDate1=transferDate(sDate1);sDate2=transferDate(sDate2);
    let aDate, oDate1, oDate2, iDays
    aDate = sDate1.split("-")
    oDate1 = new Date(aDate[1] + '-' + aDate[2] + '-' + aDate[0]) //转换为9-25-2017格式
    aDate = sDate2.split("-")
    oDate2 = new Date(aDate[1] + '-' + aDate[2] + '-' + aDate[0])
    iDays = parseInt(Math.abs(oDate1 - oDate2) / 1000 / 60 / 60 / 24) //把相差的毫秒数转换为天数
    return iDays;
}
//当前日期，格式 2018-09-02
const curDate=()=>{
   let day = new Date();
   day.setTime(day.getTime());
  return day.getFullYear()+"-" + (day.getMonth()+1) + "-" + day.getDate();
}
const getDay=(date)=>{
   date=transferDate(date);
   let weekDay = [7, 1, 2, 3, 4, 5, 6];
   let myDate = new Date(Date.parse(date));
   return weekDay[myDate.getDay()];
}
//开学日期
let   beginDate='2019-09-02';
//解析日期，解析出第几周，星期几
const  parse=(date)=>{
     let diff=DateDiff(beginDate,date) ;
     let week=Math.floor(diff/7)+1;
     let day=getDay(date);
     return {
           week:week,
           day:day
     }
}