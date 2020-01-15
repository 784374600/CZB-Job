package com.fzubb.comment.service;

import com.fzubb.comment.condition.CommentCondition;
import com.fzubb.comment.mapper.CommentMapper;
import com.fzubb.comment.model.Comment;
import com.fzubb.common.constant.CacheKey;
import com.fzubb.common.util.IDUtil;
import com.fzubb.common.util.RedisUtil;
import org.apache.dubbo.common.utils.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CommentServiceImpl implements CommentService{
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    RedisTemplate<String,Object> client;
    @Override
    public Comment add(Comment comment) {
        comment.setCommentId(IDUtil.timeIdWithParam(String.valueOf((int)((Math.random()*9+1)*1000))));
        commentMapper.addComment(comment);
        return comment;
    }

    @Override
    public Comment del(Comment comment) {
        commentMapper.deleteComment(comment);
        return  null;
    }

    @Override
    public List<Comment> getComments(CommentCondition condition) {
        String qqId=condition.getQqId();long taskId=condition.getTaskId();
        CacheKey commentCacheKey=CacheKey.Task_Comments_OrderByTime;
        String commentKey=commentCacheKey.getKeyWithParams(qqId,taskId);
        int num=condition.getNum();
        List<Comment> comments=new ArrayList<>();
        Set<Comment> cacheResult;
        long lastCommentId=condition.getLastCommentId();
        if(lastCommentId==0){
            cacheResult= RedisUtil.zgetrev(client,commentKey,0, num);
        } else{
            cacheResult=RedisUtil.zgetrevByScore(client, commentKey, 0, lastCommentId, 1,num);
        }
        if(!CollectionUtils.isEmpty(cacheResult)){
            comments.addAll(cacheResult);
        }
        if(comments.size()<num){
            long last=0;
            if(comments.size()>0) {
                last = comments.get(comments.size() - 1).getCommentId();
            }
            condition.setLastCommentId(last);condition.setNum(num-comments.size());
            comments=commentMapper.getComments(condition);
            /*此处或许可以做优化，用消息队列来加快速度*/
            for (Comment comment:comments){
                RedisUtil.zput(client,commentKey, comment, comment.getCommentId());
            }
        }
        return comments;
    }
}
