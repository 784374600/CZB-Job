package com.fzubb.model.response;

public class PageResponse<T> extends BaseResponse<T>{
     T[] data;
     boolean  isOver;
     public void setData(T[] data) {
        this.data = data;
    }

    public boolean isOver() {
        return isOver;
    }

    public void setOver(boolean over) {
        isOver = over;
    }
}
