const hex2rgba=(sColor,a)=>{
    var sColorChange = [];
    for(var i = 1;i<7;i+=2){
        sColorChange.push(parseInt("0x"+sColor.substr(i,2)))
        //也可以用slice方法
        //sColorChange.push(parseInt("0x"+sColor.slice(i,i+2)))
    }
  return "rgba("+sColorChange.join(",")+","+a+")";
}
const hex2rgb=(sColor,a)=>{
    var sColorChange = [];
    for(var i = 1;i<7;i+=2){
        sColorChange.push(parseInt("0x"+sColor.substr(i,2)))
        //也可以用slice方法
        //sColorChange.push(parseInt("0x"+sColor.slice(i,i+2)))
    }
  return "rgb("+sColorChange.join(",")+")";
}
const colorSet=['#e54d42','#f37b1d','#fbbd08','#8dc63f','#39b54a','#1cbbb4','#0081ff','#6739b6','#9c26b0','#e03997','#a5673f','#8799a3']
//s1为已中选颜色，s2为
const randomColor=(s1)=>{
    let key=(Math.floor(Math.random()*100))%colorSet.length;
    console.log(key);
    return colorSet[key];
}
const color_main='#39b54a';
module.exports={
    hex2rgba:hex2rgba,
    hex2rgb:hex2rgb,
    colors:colorSet,
    randomColor:randomColor,
    color_main:color_main
}