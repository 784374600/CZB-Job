package com.fzubb.client.dubboservice;

import com.fzubb.dao.model.dto.Task;
import com.fzubb.client.request.BaseRequest;
import com.fzubb.dao.model.dto.Comment;
import com.fzubb.dao.model.dto.PublicTask;
import com.fzubb.client.response.BaseResponse;

public interface FZUBBWriteService extends ParamsCheck{
    /**绑定与解绑学生信息*/
    BaseResponse<Object> bindStudent(BaseRequest request);
    BaseResponse<Object>  unbindStudent(BaseRequest request);

    /**增删任务*/
    BaseResponse<Task>   addTask(BaseRequest request);
    BaseResponse<Task>  deleteTask(BaseRequest request);

    /**公开与取消公开任务*/
    BaseResponse<PublicTask>  addPublicTask(BaseRequest request);
    BaseResponse<PublicTask> deletePublicTask(BaseRequest request);

    /**添加删除任务*/
    BaseResponse<Comment> addComment(BaseRequest request);
    BaseResponse<Object> deleteComment(BaseRequest request);

    /**点赞操作，+1或-1*/
    BaseResponse<Object>  thumbOprate(BaseRequest request);

    /**上传和删除图片*/
    BaseResponse<Object> uploadPhoto(BaseRequest request);
    BaseResponse<Object> delPhoto(BaseRequest request);
 }
