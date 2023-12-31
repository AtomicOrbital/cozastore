package com.example.cozastore.service;

import com.example.cozastore.entity.BlogEntity;
import com.example.cozastore.entity.UserEntity;
import com.example.cozastore.payload.request.BlogRequest;
import com.example.cozastore.payload.response.BlogResponse;
import com.example.cozastore.repository.BlogRepository;
import com.example.cozastore.service.imp.BlogServiceImp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BlogService implements BlogServiceImp {
    @Autowired
    private BlogRepository blogRepository;

    private final Logger logger = LoggerFactory.getLogger(BlogService.class);

    @Override
    public List<BlogResponse> getAllBlogs() {
        List<BlogResponse> responseList = new ArrayList<>();
        List<BlogEntity> entityList = blogRepository.findAll();
        for (BlogEntity entity: entityList) {
            BlogResponse response = new BlogResponse();
            response.setId(entity.getId());
            response.setTitle(entity.getTitle());
            response.setContent(entity.getContent());
            response.setImage(entity.getImage());
            response.setCreateDate(entity.getCreateDate());
            response.setTags(entity.getTags());
            if(entity.getUser() != null ){
                response.setIdUser(entity.getUser().getId());
            } else {
                response.setIdUser(null);
            }

            responseList.add(response);
        }
        return responseList;
    }

    @Override
    public boolean insertBlog(BlogRequest blogRequest) {
        boolean isSuccess = false;
        BlogEntity blog = new BlogEntity();
        blog.setTitle(blogRequest.getTitle());
        blog.setContent(blogRequest.getContent());
        blog.setImage(blogRequest.getImage());
        blog.setCreateDate(blogRequest.getCreateDate());
        blog.setTags(blogRequest.getTags());
        UserEntity user = new UserEntity();
        user.setId(blogRequest.getIdUser());
        blog.setUser(user);
        try {
            blogRepository.save(blog);
            isSuccess = true;
        } catch (Exception e) {
            logger.info("Unable to insert blog. Error: " + e.getLocalizedMessage());
        }
        return isSuccess;
    }

    @Override
    public BlogResponse getBlogById(int id) {
        BlogResponse response = new BlogResponse();
        Optional<BlogEntity> optionalBlogResponse = blogRepository.findById(id);
        if (optionalBlogResponse.isPresent()){
            BlogEntity blog = optionalBlogResponse.get();
            response.setId(blog.getId());
            response.setTitle(blog.getTitle());
            response.setContent(blog.getContent());
            response.setImage(blog.getImage());
            response.setCreateDate(blog.getCreateDate());
            response.setTags(blog.getTags());
            if(response.getIdUser() != null) {
                response.setIdUser(blog.getUser().getId());
            }
        } else {
            logger.info("Can't find blog from Id. Please check your ID again.");
        }
        return response;
    }

    @Override
    public boolean deleteBlogById(int id) {
        boolean isSuccess = false;
        try {
            blogRepository.deleteById(id);
            isSuccess = true;
        } catch (Exception e) {
            logger.info("Unable to delete blog from ID. Please check your Id again.");
        }
        return isSuccess;
    }

    @Override
    public boolean modifyBlogById(int id, BlogRequest blogRequest) {
        boolean isSuccess = false;
        Optional<BlogEntity> optionalBlogEntity = blogRepository.findById(id);
        if (optionalBlogEntity.isPresent()){
            BlogEntity blog = optionalBlogEntity.get();
            blog.setTitle(blogRequest.getTitle());
            blog.setContent(blogRequest.getContent());
            blog.setImage(blogRequest.getImage());
            blog.setCreateDate(blogRequest.getCreateDate());
            blog.setTags(blogRequest.getTags());
            UserEntity user = new UserEntity();
            user.setId(blogRequest.getIdUser());
            blog.setUser(user);
            blogRepository.save(blog);
            isSuccess = true;
        } else {
            logger.info("Can't find blog from ID. Please check your ID again.");
        }
        return isSuccess;
    }
}
