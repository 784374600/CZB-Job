package com.fzubb.dubboservice;

import com.fzubb.condition.PublicTaskCondition;
import com.fzubb.model.dto.Task;
import com.fzubb.model.dto.TaskVo;
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
