package com.artifact.service;

import com.artifact.dao.CommentDao;
import com.artifact.domain.Comment;
import com.artifact.domain.User;
import com.artifact.domain.Views;
import com.artifact.dto.EventType;
import com.artifact.dto.ObjectType;
import com.artifact.utils.WsSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.BiConsumer;

@Service
public class CommentService {
    private final CommentDao commentDao;
    private final BiConsumer<EventType, Comment> wsSender;

    @Autowired
    public CommentService(CommentDao commentDao, WsSender wsSender) {
        this.commentDao = commentDao;
        this.wsSender = wsSender.getSender(ObjectType.COMMENT, Views.FullComment.class);
    }

    public Comment create(Comment comment, User user) {
        comment.setAuthor(user);
        Comment commentFromDb = commentDao.save(comment);

        wsSender.accept(EventType.CREATE, commentFromDb);

        return commentFromDb;
    }
}
