package com.example.cozastore.service.imp;

import com.example.cozastore.payload.request.CommentRequest;
import com.example.cozastore.payload.response.CommentResponse;

import java.util.List;

public interface CommentServiceImp {
    List<CommentResponse> getAllComments();

    boolean insertComment(CommentRequest commentRequest);

    CommentResponse getCommentById(int id);

    boolean deleteCommentById(int id);

    boolean modifyCommentById(int id, CommentRequest commentRequest);
}
