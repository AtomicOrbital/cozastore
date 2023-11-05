package com.example.cozastore.service.imp;

import com.example.cozastore.payload.request.BlogRequest;
import com.example.cozastore.payload.response.BlogResponse;

import java.util.List;

public interface BlogServiceImp {

    List<BlogResponse> getAllBlogs();

    boolean insertBlog(BlogRequest blogRequest);

    BlogResponse getBlogById(int id);

    boolean deleteBlogById(int id);

    boolean modifyBlogById(int id, BlogRequest blogRequest);
}
