package com.example.cozastore.controller;

import com.example.cozastore.payload.request.BlogRequest;
import com.example.cozastore.payload.response.BlogResponse;
import com.example.cozastore.service.imp.BlogServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blogs")
public class BlogController {
    @Autowired
    private BlogServiceImp blogServiceImp;

    @GetMapping("")
    public ResponseEntity<?> getBlogs(){
        List<BlogResponse> responseList = blogServiceImp.getAllBlogs();
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> insertBlog(@RequestBody BlogRequest blogRequest){
        boolean isSuccess = blogServiceImp.insertBlog(blogRequest);
        return new ResponseEntity<>(isSuccess, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBlogFromId(@PathVariable int id){
        BlogResponse response = blogServiceImp.getBlogById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBlogFromId(@PathVariable int id){
        boolean isSuccess = blogServiceImp.deleteBlogById(id);
        return new ResponseEntity<>(isSuccess, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> modifyBlogFromId(@PathVariable int id, @RequestBody BlogRequest blogRequest){
        boolean isSuccess = blogServiceImp.modifyBlogById(id, blogRequest);
        return new ResponseEntity<>(isSuccess, HttpStatus.OK);
    }
}
