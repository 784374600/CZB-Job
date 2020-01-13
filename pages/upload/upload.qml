<view class="container" bindtap="reSet">

<view class="cu-list tags">
    <view class="cu-item tag arrow"  style="display:flex;flex-direction:row;">
      <view class="content" style="display:flex;flex-direction:row;">
        <text class="cuIcon-tagfill text-red"></text>
        <text class="text-grey tag-label">主题</text>
      </view>
      <view class="action" style="display: flex;flex-direction:row;align-items: center">
        <view class="cu-tag round bg-orange light tag-action">音乐</view>
        <view class="cu-tag round bg-olive light tag-action">电影</view>
        <view class="cu-tag round bg-blue light tag-action">旅行</view>
      </view>
    </view>
    <view class="cu-item tag arrow">
      <view class="content" style="display:flex;flex-direction:row;">
        <text class="cuIcon-warn text-green"></text>
        <text class="text-grey tag-label">备注</text>
      </view>
      <view class="action tag-action" style="width:80%">
        <input class="text-grey text-sm" style="width:100%;text-align:right" placeholder="写下你的小期待吧" maxlength="20"/>
      </view>
    </view>
    <view class="cu-item tag arrow"  style="display:flex;flex-direction:row;justify-content:space-between">
        <view class="content" style="display:flex;flex-direction:row;">
           <text class="cuIcon-countdown  text-purple"></text>
           <text class="text-grey tag-label">截止日期</text>
        </view>
        <view catchtap="chooseTime" >{{year}}年{{month}}月{{day}}日</view>
    </view>
</view>


<textarea class="upload-content" maxlength="-1" placeholder="神说，要有光" bindinput="reSet" bindfocus="reSet"/>

<view class="photos">
    <view style="flex-direction:row;justify-content:space-between;width:100%;">
        <view>图片上传</view><view>{{photos.length}}/5</view>
    </view>
   <view class="photo" qq:for="{{photos}}" qq:key="*this">
   <image src="{{photos[index]}}"/>
   </view>
   <view class="photo" bindtap="choosePhotos"><text class="cu-item cuIcon-roundaddfill photo-add" ></text></view>  
</view>

<view class="time" qq:if="{{showTimePicker}}">
        <picker-view indicator-style="height: 50px;"style="width: 100%; height: 300px;"value="{{value}}"
                   catchchange="bindChange">
            <picker-view-column>
                <view qq:for="{{years}}" qq:key="*this" style="line-height: 50px">{{item}}年</view>
            </picker-view-column>
            <picker-view-column>
                <view qq:for="{{months}}" qq:key="*this" style="line-height: 50px">{{item}}月</view>
            </picker-view-column>
            <picker-view-column>
                <view qq:for="{{days}}" qq:key="*this" style="line-height: 50px">{{item}}日</view>
            </picker-view-column>
        </picker-view>
</view>


</view>