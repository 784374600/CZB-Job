package com.fzubb.client.dubboservice;

import com.fzubb.common.ExcepTion.WrongParamException;
import com.fzubb.client.request.BaseRequest;
import com.fzubb.common.util.StringUtil;

import java.util.Map;
import java.util.Objects;

public interface ParamsCheck {
    /*检查参数，默认检查qqId*/
    default void checkParams(BaseRequest request , String... params) {
        if(request==null || StringUtil.isEmpty(request.getQqId())) {
            throw new WrongParamException("参数qqId不存在");
        }
        Map<String,Object> map=request.getParams();
        if(map==null) {
            return;
        }
        for (String param: params
        ) {
            if(Objects.isNull(map.get(param))) {
                throw new WrongParamException("参数" + param + "不存在");
            }
        }
    }
}
