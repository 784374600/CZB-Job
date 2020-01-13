<view class="container">
<scroll-view scroll-x class="bg-white nav tab" scroll-with-animation="true">
  <view class="flex text-center tab" >
    <view class="cu-item flex-sub tab" qq:for="{{4}}" style="color:{{index==tabCur?color_tab:''}}"   qq:key bindtap="tabSelect" data-id="{{index}}">
      Tab{{index}}
    </view>
  </view>
</scroll-view>
<article_card style="width:100%"></article_card>
</view>