//给每个请求添加qqId
const handleParams=(obj)=>{
   obj.qqId=this.qqId;
}
//将http请求返回结果处理，提取其中后台的响应部分
const resolveRes=(res=>{
     let result;
     let data=res.data;
     if(data.code==0){
        result.code=0;
        result.data=data.data;
     }else{
         result.code=data.code;
         result.msg=data.msg;
     }
     return result;
});
//发起http请求
const request=(url,headers,body,method)=>{
     let result;
     let data,code,msg;
     handleParams(body);
     qq.request({
          url:url,
         header:headers,
         data:body,
         method:method,
         success(res){
            result= resolveRes(res);
         }
     });

     return result;
}
//get请求
const get=(url,headers,body)=>{
     return request(url,headers,body,'GET');
}
//post请求
const post=(url,headers,body)=>{
     return request(url,headers,body,'POST');
}
module.exports={
    get:get,
    post:post,
}



