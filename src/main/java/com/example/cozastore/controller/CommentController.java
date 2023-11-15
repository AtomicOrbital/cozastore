package com.example.cozastore.controller;

import com.example.cozastore.payload.request.CommentRequest;
import com.example.cozastore.payload.response.BaseResponse;
import com.example.cozastore.payload.response.CommentResponse;
import com.example.cozastore.service.imp.CommentServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    private CommentServiceImp commentServiceImp;

    @GetMapping("")
    public ResponseEntity<?> getComments(){
        BaseResponse baseResponse = new BaseResponse();
        try {
            List<CommentResponse> responseList = commentServiceImp.getAllComments();
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
    public ResponseEntity<?> insertComment(@RequestBody  CommentRequest commentRequest){
        BaseResponse baseResponse = new BaseResponse();
        try {
            boolean isSuccess = commentServiceImp.insertComment(commentRequest);
            baseResponse.setMessage("Created Comment Successfully");
            baseResponse.setData(isSuccess);
            return ResponseEntity.ok(baseResponse);
        }catch (Exception e){
            baseResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(baseResponse);
        }
    }

    @GetMapping ("/{id}")
    public ResponseEntity<?> getCommentFromId(@PathVariable int id){
        BaseResponse baseResponse = new BaseResponse();
        try {
            CommentResponse response = commentServiceImp.getCommentById(id);
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
    public ResponseEntity<?> deleteCommentFromId(@PathVariable int id){
        BaseResponse baseResponse = new BaseResponse();
        try {
            boolean isSuccess = commentServiceImp.deleteCommentById(id);
            baseResponse.setMessage("Deleted Comment Successfully");
            baseResponse.setData(isSuccess);
            return ResponseEntity.ok(baseResponse);
        }catch (Exception e){
            baseResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(baseResponse);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> modifyCommentFromId(@PathVariable int id, @RequestBody CommentRequest commentRequest){
        BaseResponse baseResponse = new BaseResponse();
        try {
            boolean isSuccess = commentServiceImp.modifyCommentById(id, commentRequest);
            baseResponse.setMessage("Updated Comment Successfully");
            baseResponse.setData(isSuccess);
            return ResponseEntity.ok(baseResponse);
        }catch (Exception e){
            baseResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(baseResponse);
        }

    }
}
