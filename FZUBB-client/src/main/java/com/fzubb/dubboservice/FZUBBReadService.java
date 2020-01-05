package com.fzubb.dubboservice;

import com.fzubb.model.vo.HomeDataPublicTaskVo;
import com.fzubb.querycondition.PublicTaskCondition;
import com.fzubb.model.vo.TaskVo;
import com.fzubb.model.request.BaseRequest;
import com.fzubb.model.request.PageRequest;
import com.fzubb.model.response.BaseResponse;
import com.fzubb.model.response.PageResponse;

import javax.xml.ws.Response;

public interface FZUBBReadService {
    BaseResponse<Object>  login(BaseRequest request);
    PageResponse<HomeDataPublicTaskVo> queryHomeDataPublicTasks(PageRequest request);
    BaseResponse<TaskVo> queryTaskInfo(PageRequest request);
}
