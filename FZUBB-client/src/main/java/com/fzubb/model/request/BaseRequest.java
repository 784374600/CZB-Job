package com.fzubb.model.request;

import java.io.Serializable;
import java.util.Map;

public class BaseRequest implements Serializable {
    private static final long serialVersionUID = -3182103795897993357L;

    //用户pin
    private String qqId;
    //请求对象map
    private Map<String, Object> params;

    public String getQqId() {
        return qqId;
    }

    public void setQqId(String qqId) {
        this.qqId = qqId;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
    public   <T>  T  getParam(String  param,Class<T>  Class){
          return  params==null?null:(T)(params.get(param));
    }
    public   String  getParam(String  param){
        return  params==null?null:(String)(params.get(param));
    }
}
