package com.fzubb.client.request;

public class PageRequest extends  BaseRequest {
        Integer  index;
        Integer  limit;

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
