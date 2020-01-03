package com.fzubb.Model;

import lombok.Data;

@Data
public class Photo {
    String  position;
    String name;
    String suffix;
    public String url(){
        return  position+"/"+name;
    }
}
