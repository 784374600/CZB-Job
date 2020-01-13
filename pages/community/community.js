const app=getApp;


Page({
    data: {
    tabCur:1,
    scrollLeft:0,
    color_tab:'#35B649'
  },
  tabSelect(e) {
    this.setData({
      tabCur: e.currentTarget.dataset.id,
      scrollLeft: (e.currentTarget.dataset.id-1)*60
    })
  },onLoad(){
     
  }

})