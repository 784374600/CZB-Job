const applyObject=(obj)=>{
   let str ;let index=0;
   for (let key in obj) {
       if(index>0){
            str=str+' ; '+key+' :'+obj[key];
       }else{
           str=key+' : '+obj[key];
           index=index+1;
       }   
   }
   return str;
}
module.exports={
    applyObject:applyObject
}