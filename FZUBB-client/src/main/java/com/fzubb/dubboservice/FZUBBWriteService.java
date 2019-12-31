package com.fzubb.dubboservice;

import com.fzubb.model.request.BaseRequest;
import com.fzubb.model.response.BaseResponse;

public interface FZUBBWriteService {
    BaseResponse<Object> bindStudent(BaseRequest request);
    BaseResponse<Object>  unbindStudent(BaseRequest request);
}
