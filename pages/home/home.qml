<view class="container">
<swiper class="card-swiper {{DotStyle?'square-dot':'round-dot'}} home-swiper bg-green" indicator-dots="true" circular="true" autoplay="true" interval="5000" duration="500" bindchange="{{cardSwiper}}" indicator-color="#8799a3" indicator-active-color="{{theme}}">
  <swiper-item qq:for="{{swiperList}}"  class="{{cardCur==index?'cur':''}}">
    <view class="swiper-item">
      <image src="{{item.url}}" mode="aspectFill" qq:if="{{item.type=='image'}}"></image>
      <video src="{{item.url}}" autoplay loop muted show-play-btn="{{false}}" controls="{{false}}" objectFit="cover" qq:if="{{item.type=='video'}}"></video>
    </view>
  </swiper-item>
</swiper>
<view class="cards">
    <advice_card class="card" qq:for="{{cards}}" color_main="{{item.color}}"/>
</view>
</view>
