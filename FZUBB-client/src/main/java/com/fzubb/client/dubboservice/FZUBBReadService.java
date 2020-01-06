package com.fzubb.client.dubboservice;

import com.fzubb.dao.model.dto.Task;
import com.fzubb.client.request.BaseRequest;
import com.fzubb.client.request.PageRequest;
import com.fzubb.client.response.BaseResponse;
import com.fzubb.client.response.PageResponse;
import com.fzubb.dao.model.vo.HomeDataPublicTaskVo;
import com.fzubb.dao.model.vo.PublicTaskVo;

public interface FZUBBReadService  extends ParamsCheck{
    /**登录*/
    BaseResponse<Object> login(BaseRequest request);
    /**获取任务社区首页信息*/
    PageResponse<HomeDataPublicTaskVo> queryHomeDataPublicTasks(PageRequest request);
    /**查询具体任务*/
    BaseResponse<Task> queryTask(PageRequest request);
    /**查询公开任务*/
    BaseResponse<PublicTaskVo> queryPublicTask(BaseRequest request);

}
