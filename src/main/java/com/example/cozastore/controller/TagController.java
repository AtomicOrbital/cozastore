package com.example.cozastore.controller;

import com.example.cozastore.payload.request.SizeRequest;
import com.example.cozastore.payload.request.TagRequest;
import com.example.cozastore.payload.response.BaseResponse;
import com.example.cozastore.payload.response.SizeResponse;
import com.example.cozastore.payload.response.TagResponse;
import com.example.cozastore.service.imp.TagServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
public class TagController {
    @Autowired
    private TagServiceImp tagServiceImp;

    @PostMapping
    public ResponseEntity<BaseResponse> createTag(@RequestBody TagRequest tagRequest){
        BaseResponse baseResponse = new BaseResponse();
        try {
            TagResponse tagResponse = tagServiceImp.createTag(tagRequest);
            baseResponse.setData(tagResponse);
            baseResponse.setMessage("Tag created successfully");
            return ResponseEntity.ok(baseResponse);
        } catch(Exception e){
            baseResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(baseResponse);
        }
    }

    @GetMapping
    public ResponseEntity<BaseResponse> getAllTags(){
        BaseResponse baseResponse = new BaseResponse();
        try {
            List<TagResponse> tags = tagServiceImp.getAllTags();
            baseResponse.setMessage("SUCCESS");
            baseResponse.setData(tags);
            return ResponseEntity.ok(baseResponse);
        }catch (Exception e){
            baseResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(baseResponse);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse> getTagById(@PathVariable int id){
        BaseResponse baseResponse = new BaseResponse();
        try {
            TagResponse tagResponse = tagServiceImp.getTagById(id);
            baseResponse.setMessage("SUCCESS");
            baseResponse.setData(tagResponse);
            return ResponseEntity.ok(baseResponse);
        }catch (Exception e){
            baseResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(baseResponse);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse> updateTag(@PathVariable int id, @RequestBody TagRequest tagRequest){

        BaseResponse baseResponse = new BaseResponse();
        try {
            TagResponse updatedTag = tagServiceImp.updateTag(id, tagRequest);
            baseResponse.setMessage("Tag Updated Successfully");
            baseResponse.setData(updatedTag);
            return ResponseEntity.ok(baseResponse);
        }catch (Exception e){
            baseResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(baseResponse);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse> deleteTag(@PathVariable int id){
        BaseResponse baseResponse = new BaseResponse();
        try {
            tagServiceImp.deleteTag(id);
            baseResponse.setMessage("Tag Deleted Successfully");
            return ResponseEntity.ok(baseResponse);
        }catch (Exception e){
            baseResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(baseResponse);
        }
    }
}
