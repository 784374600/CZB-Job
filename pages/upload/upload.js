const date = new Date()
const years = []
const months = []
const days = [] 
for (let i = date.getFullYear(); i <= date.getFullYear()+1; i++) {
  years.push(i)
}

for (let i = 1; i <= 12; i++) {
  months.push(i)
}

for (let i = 1; i <= 31; i++) {
  days.push(i)
}

Page({
  data: {
      //时间picker数据
    years,
    year: date.getFullYear(),
    months,
    month: 2,
    days,
    day: 2,
    value: [0, 5, 5],
    //时间选择开关
    showTimePicker:false,
    //已选图片
    photos:[],
    //已选图片路径
    photoPaths:[],
    //题目内容
    content:'',
    theme:'',
    note:''
  },
  bindChange(e) {
    const val = e.detail.value
    this.setData({
      year: this.data.years[val[0]],
      month: this.data.months[val[1]],
      day: this.data.days[val[2]]
    })
  },
  choosePhotos(e){
      let that=this;
      qq.chooseImage({
          success(res){
            that.setData({photos:res.tempFilePaths})
            for(let i=0;i<res.tempFilePaths.length;i++){
              console.log(res.tempFilePaths[i]);
            }
        }
      })
  },
  chooseTime(){
    this.setData({
      showTimePicker:!this.showTimePicke
  })
  },
  reSet(){
    this.setData({
      showTimePicker:false
  })
  }
})