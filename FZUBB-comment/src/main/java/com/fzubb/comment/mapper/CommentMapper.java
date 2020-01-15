package com.fzubb.comment.mapper;


import com.fzubb.comment.condition.CommentCondition;
import com.fzubb.comment.model.Comment;

import java.util.List;

public interface CommentMapper {
        int addComment(Comment comment);
        int deleteComment(Comment comment);
        List<Comment> getComments(CommentCondition condition);
}
