package com.example.cozastore.service.imp;

import com.example.cozastore.payload.request.BlogRequest;
import com.example.cozastore.payload.response.BlogResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BlogServiceImp {

    List<BlogResponse> getAllBlogs();

    boolean insertBlog(BlogRequest blogRequest);

    boolean insertBlog(String title, String content,
                       MultipartFile file, String createDate,
                       int idUser, String tags);

    BlogResponse getBlogById(int id);

    boolean deleteBlogById(int id);

    boolean modifyBlogById(int id, BlogRequest blogRequest);
}
