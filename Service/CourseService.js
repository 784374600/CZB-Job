const  date=require('/utils/DateUtil.js');
//课程清单
let courseList=[];
//学生所有课程信息，二维数组。第一维下标为周数，第二维为按时间顺序排列的课程
let  courses=[];

//收到数据，初始化courseList 和courses
const init=(o)=>{
     courseList=o;
    for(let i=0;i<20;i++){
        courses[i]=new Array();
    }
     for(let i=0;i<o.length;i++){
            let c=courseList[i];
            let w1=c.week1;let w2=c.week2;
            for(let j=w1;j<=w2;j++){
                  if(c.type===0 || j%c.type===c.type) {
                      for (let k = 0; k < c.teachList.length; k++) {
                          let t = c.teachList[k];
                          let teach = {
                              courseId: c.courseId,
                              teacher: c.teacher,
                              name:c.name,
                              week: j,
                              time1: t.time1,
                              time2: t.time2,
                              place: t.place,
                              day:t.day
                          };
                          courses[j].push(teach);
                      }
                  }
            }
     }
     for(let i=0;i<courses.length;i++){
            courses[i].sort((o1,o2)=>{
                   if(o1.day!==o2.day)
                       return  o1.day-o2.day;
                   return o1.time1-o2.time1;
            })
     }
}


//通过日期获取课程数据
const  getCoursesByDate=(date) =>{
       let info=date.parse(date.curDate);
       let week=info.week;let day=info.day;
       let results=[];
       for(let i=0;i<courses[week].length;i++){
             if(courses[week][i].day>day)
                 break;
             if(courses[week][i].day===day)
                 results.push(courses[week][i]);
       }
       return results;
}
