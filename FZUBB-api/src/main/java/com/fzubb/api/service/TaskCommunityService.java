package com.fzubb.api.service;

import com.fzubb.dao.model.vo.PublicTaskVo;
import com.fzubb.dao.querycondition.CommentCondition;
import com.fzubb.dao.querycondition.PublicTaskCondition;
import com.fzubb.dao.model.vo.HomeDataPublicTaskVo;

import java.util.List;

public interface TaskCommunityService {
    List<HomeDataPublicTaskVo> getHomeData(String qqId, PublicTaskCondition condition);
    PublicTaskVo getPublicTaskVo(String qqId, long taskId, CommentCondition commentCondition);
}
