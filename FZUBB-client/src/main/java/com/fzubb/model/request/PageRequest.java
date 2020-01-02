package com.fzubb.model.request;

public class PageRequest extends  BaseRequest {
        int  index;
        int  limit;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
