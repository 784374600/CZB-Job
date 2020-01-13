const colorUtil=require('../../utils/ColorUtil.js');
const objectUtil=require('../../utils/ObjectUtil.js');
Component({
    properties: {
        title:{
            type:String,
            value:'没有设置标题呢',

        },
        label:{
            type:String,
            value:'标签'
        },
        content_label:{
            type:String,
            value:'最新'
        },
        content:{
            type:Array,
            value:[{
                text:"酸柠檬校园更新了",
                time:"一个月前"
            },{
                text:"酸柠檬校园更新了",
                time:"一个月前"
            }]
        },
        color_main:{
            type:String,
            value:'#35B649'
        },
        color_rightup:{
            type:String,
            value:'#377FF6'
        }
    },
    data: {
        label_style:'',content_label_style:'',label_rightup_style:''
    },
    methods: {
        // 16进制颜色转rgba
        applyColor(o,a) {
            return colorUtil.hex2rgba(o,a);
        },
        applyObject(o){
            return objectUtil.applyObject(o);
        }

    },
    lifetimes: {
        attached() {
            let c1=this.properties.color_main;let c2=this.properties.color_rightup;
            this.setData({
                label_style:this.applyObject({"background":c1}),
                label_rightup_style:this.applyObject({"background":this.applyColor(c2,0.2),"color":this.applyColor(c2,0.9)}),
                content_label_style:this.applyObject({"border-color":this.applyColor(c1,0.5),  "color":this.applyColor(c1,0.9)})
            });


        },
        detached() {
            // 在组件实例被从页面节点树移除时执行
        },
    },
})