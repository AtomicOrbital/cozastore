package com.example.cozastore.controller;

import com.example.cozastore.payload.request.CommentRequest;
import com.example.cozastore.payload.response.CommentResponse;
import com.example.cozastore.service.CommentService;
import com.example.cozastore.service.imp.CommentServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    private CommentServiceImp commentServiceImp;

    @GetMapping("")
    public ResponseEntity<?> getComments(){
        List<CommentResponse> responseList = commentServiceImp.getAllComments();
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> insertComment(@RequestBody  CommentRequest commentRequest){
        boolean isSuccess = commentServiceImp.insertComment(commentRequest);
        return new ResponseEntity<>(isSuccess, HttpStatus.OK);
    }
}
