package com.fzubb.client.response;

import com.fzubb.client.response.BaseResponse;
import org.apache.dubbo.common.utils.CollectionUtils;

import java.util.Collection;

public class PageResponse<T> extends BaseResponse<T> {
     T[] data;
     boolean  isOver;
     public void setData(T[] data) {
        this.data = data;
     }
    public void setData(Collection<T> data) {
         if(CollectionUtils.isEmpty(data))
             return;
        this.data = (T[])data.toArray();
    }

    public boolean isOver() {
        return isOver;
    }

    public void setOver(boolean over) {
        isOver = over;
    }
}
