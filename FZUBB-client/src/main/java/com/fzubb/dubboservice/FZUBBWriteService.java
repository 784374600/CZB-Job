package com.fzubb.dubboservice;

import com.fzubb.model.dto.Comment;
import com.fzubb.model.dto.PublicTask;
import com.fzubb.model.dto.Task;
import com.fzubb.model.request.BaseRequest;
import com.fzubb.model.response.BaseResponse;

public interface FZUBBWriteService {
    BaseResponse<Object> bindStudent(BaseRequest request);
    BaseResponse<Object>  unbindStudent(BaseRequest request);
    BaseResponse<Task>   addTask(BaseRequest request);
    BaseResponse<Task>  deleteTask(BaseRequest request);
    BaseResponse<PublicTask>  addPublicTask(BaseRequest request);
    BaseResponse<PublicTask> deletePublicTask(BaseRequest request);
    BaseResponse<Comment> addComment(BaseRequest request);
    BaseResponse<Comment> deleteComment(BaseRequest request);
 }
