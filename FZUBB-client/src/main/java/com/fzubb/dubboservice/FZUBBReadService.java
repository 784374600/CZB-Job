package com.fzubb.dubboservice;

import com.fzubb.model.request.BaseRequest;
import com.fzubb.model.response.BaseResponse;

public interface FZUBBReadService {
    BaseResponse<Object>  login(BaseRequest request);
    BaseResponse<Object>  queryTasksByCourse(BaseRequest request);

}
