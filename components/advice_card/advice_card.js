Component({
  properties: {
    title:{
        type:String,
        value:'没有设置标题呢'
    },
    lable:{
        type:String,
        value:'标签'
    },
    content:{
        type:Array,
        value:[{
             content:"酸柠檬校园更新了",
             time:"一个月前"
        },{
             content:"酸柠檬校园更新了",
             time:"一个月前"
        }]
    },
    color_main:{
        type:String,
        value:'#35B649'
    },
    color_rightup:{
        type:String,
        value:'#35B649'
    }
  },
  data: {
  },
  methods: {
    // 这里是一个自定义方法
    customMethod() {},

  },
  lifetimes: {
    attached() {
        console.log('哈哈哈');
      console.log("pro:"+this.properties);
    },
    detached() {
      // 在组件实例被从页面节点树移除时执行
    },
  },
})