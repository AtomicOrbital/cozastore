package com.example.cozastore.service;

import com.example.cozastore.entity.BlogEntity;
import com.example.cozastore.entity.CommentEntity;
import com.example.cozastore.payload.request.CommentRequest;
import com.example.cozastore.payload.response.CommentResponse;
import com.example.cozastore.repository.CommentRepository;
import com.example.cozastore.service.imp.CommentServiceImp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService implements CommentServiceImp {
    @Autowired
    private CommentRepository commentRepository;

    private final Logger logger = LoggerFactory.getLogger(CommentService.class);

    @Override
    public List<CommentResponse> getAllComments() {
        List<CommentResponse> responseList = new ArrayList<>();
        List<CommentEntity> entityList = commentRepository.findAll();
        for (CommentEntity entity: entityList) {
            CommentResponse response = new CommentResponse();
            response.setId(entity.getId());
            response.setName(entity.getName());
            response.setContent(entity.getName());
            response.setEmail(entity.getEmail());
            response.setWebsite(entity.getWebsite());
            response.setIdBlog(entity.getBlog().getId());
            responseList.add(response);
        }
        return responseList;
    }

    @Override
    public boolean insertComment(CommentRequest commentRequest) {
        boolean isSuccess = false;
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setName(commentRequest.getName());
        commentEntity.setContent(commentRequest.getContent());
        commentEntity.setEmail(commentRequest.getEmail());
        commentEntity.setWebsite(commentRequest.getWebsite());
        BlogEntity blog = new BlogEntity();
        blog.setId(commentRequest.getIdBlog());
        commentEntity.setBlog(blog);
        try {
            commentRepository.save(commentEntity);
            isSuccess = true;
        } catch (Exception e) {
            logger.error("Unable to insert comment. Error: " + e.getLocalizedMessage());
        }
        return isSuccess;
    }

    @Override
    public CommentResponse getCommentById(int id) {
        return null;
    }

    @Override
    public boolean deleteCommentById(int id) {
        return false;
    }

    @Override
    public boolean modifyCommentById(int id, CommentRequest commentRequest) {
        return false;
    }
}
