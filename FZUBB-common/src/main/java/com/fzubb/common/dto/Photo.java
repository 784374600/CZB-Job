package com.fzubb.common.dto;

import lombok.Data;

@Data
public class Photo {
    String  pre;
    String  path;
    String name;
    public String url(){
        return pre+path+"/"+name;
    }

    public Photo(String pre, String path, String name) {
        this.pre = pre;
        this.path = path;
        this.name = name;
    }

    public Photo() {
    }
}
