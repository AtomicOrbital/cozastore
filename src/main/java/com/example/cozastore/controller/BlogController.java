package com.example.cozastore.controller;

import com.example.cozastore.payload.request.BlogRequest;
import com.example.cozastore.payload.response.BaseResponse;
import com.example.cozastore.payload.response.BlogResponse;
import com.example.cozastore.service.imp.BlogServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blogs")
public class BlogController {
    @Autowired
    private BlogServiceImp blogServiceImp;

    @GetMapping("")
    public ResponseEntity<?> getBlogs(){
        BaseResponse baseResponse = new BaseResponse();
        try {
            List<BlogResponse> responseList = blogServiceImp.getAllBlogs();
            baseResponse.setMessage("SUCCESS");
            baseResponse.setData(responseList);
            return ResponseEntity.ok(baseResponse);
        }catch (Exception e){
            baseResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(baseResponse);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("")
    public ResponseEntity<?> insertBlog(@RequestBody BlogRequest blogRequest){
        BaseResponse baseResponse = new BaseResponse();
        try {
            boolean isSuccess = blogServiceImp.insertBlog(blogRequest);
            baseResponse.setMessage("Created Blog Successfully");
            baseResponse.setData(isSuccess);
            return ResponseEntity.ok(baseResponse);
        }catch (Exception e){
            baseResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(baseResponse);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBlogFromId(@PathVariable int id){
        BaseResponse baseResponse = new BaseResponse();
        try {
            BlogResponse response = blogServiceImp.getBlogById(id);
            baseResponse.setMessage("SUCCESS");
            baseResponse.setData(response);
            return ResponseEntity.ok(baseResponse);
        }catch (Exception e){
            baseResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(baseResponse);
        }

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBlogFromId(@PathVariable int id){
        BaseResponse baseResponse = new BaseResponse();
        try {
            boolean isSuccess = blogServiceImp.deleteBlogById(id);
            baseResponse.setMessage("Deleted Blog Successfully");
            baseResponse.setData(isSuccess);
            return ResponseEntity.ok(baseResponse);
        }catch (Exception e){
            baseResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(baseResponse);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> modifyBlogFromId(@PathVariable int id, @RequestBody BlogRequest blogRequest){
        BaseResponse baseResponse = new BaseResponse();
        try {
            boolean isSuccess = blogServiceImp.modifyBlogById(id, blogRequest);
            baseResponse.setMessage("Updated Blog Successfully");
            baseResponse.setData(isSuccess);
            return ResponseEntity.ok(baseResponse);
        }catch (Exception e){
            baseResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(baseResponse);
        }
    }
}
