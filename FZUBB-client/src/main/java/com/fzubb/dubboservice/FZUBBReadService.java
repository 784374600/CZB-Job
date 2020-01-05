package com.fzubb.dubboservice;

import com.fzubb.querycondition.PublicTaskCondition;
import com.fzubb.model.vo.TaskVo;
import com.fzubb.model.request.BaseRequest;
import com.fzubb.model.request.PageRequest;
import com.fzubb.model.response.BaseResponse;
import com.fzubb.model.response.PageResponse;

public interface FZUBBReadService {
    BaseResponse<Object>  login(BaseRequest request);
    BaseResponse<Object>  queryTasksByCourse(BaseRequest request);
    PageResponse<TaskVo>   queryPublicTasksByCondition(PageRequest request, PublicTaskCondition condition);
    BaseResponse<TaskVo> queryTaskInfo(PageRequest request);
}
