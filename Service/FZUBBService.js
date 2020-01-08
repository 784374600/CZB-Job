const http=require('/utils/HttpUtil.js');
const app=getApp;

const pre='http://';
const login_url=pre+'http:***';
const getHomeData_url=pre+'http:***';
const bind_url=pre+'http:****';
const unbind_url=pre+'http:****';
const queryPublicTask_url=pre+"dkfk";
const queryTask_url=pre+"fff";
const addTask_url=pre+"fff";
const delTask_url=pre+"fff";

//登录
const login=(obj)=>{
      return http.get(login_url,null,obj);
}

// 首页 obj格式{
//     qqId:
 //      ......
// }
const getHomeData=(obj)=>{
      return http.get(getHomeData_url,null,obj);
}

//绑定教务处
const bind=(obj)=>{
     return http.get(bind_url,null,obj);
}

//解绑教务处
const unbind=(obj)=>{
     return http.get(unbind_url,null,obj);
}

//查询公开任务
const queryPublicTask=(obj)=>{
    return http.get(queryPublicTask_url,null,obj);
}

//添加任务
const addTask=(obj)=>{
   return http.get(addTask_url,null,obj);
}

//删除任务
const delTask=(obj)=>{
   return http.get(delTask_url,null,obj);
}
//查询任务
const queryTask=(obj)=>{
    return http.get(queryTask_url,null,obj);
}

//