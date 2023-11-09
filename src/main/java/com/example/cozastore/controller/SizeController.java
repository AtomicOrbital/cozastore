package com.example.cozastore.controller;

import com.example.cozastore.payload.request.ColorRequest;
import com.example.cozastore.payload.request.SizeRequest;
import com.example.cozastore.payload.response.BaseResponse;
import com.example.cozastore.payload.response.ColorResponse;
import com.example.cozastore.payload.response.SizeResponse;
import com.example.cozastore.service.imp.SizeServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sizes")
public class SizeController {
    @Autowired
    private SizeServiceImp sizeServiceImp;

    @PostMapping
    public ResponseEntity<BaseResponse> createSize(@RequestBody SizeRequest sizeRequest){
        BaseResponse baseResponse = new BaseResponse();
        try {
            SizeResponse sizeResponse = sizeServiceImp.createSize(sizeRequest);
            baseResponse.setData(sizeResponse);
            baseResponse.setMessage("Size created successfully");
            return ResponseEntity.ok(baseResponse);
        } catch(Exception e){
            baseResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(baseResponse);
        }
    }

    @GetMapping
    public ResponseEntity<BaseResponse> getAllColors(){
        BaseResponse baseResponse = new BaseResponse();
        try {
            List<SizeResponse> colors = sizeServiceImp.getAllSizes();
            baseResponse.setMessage("SUCCESS");
            baseResponse.setData(colors);
            return ResponseEntity.ok(baseResponse);
        }catch (Exception e){
            baseResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(baseResponse);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse> getSizeById(@PathVariable int id){
        BaseResponse baseResponse = new BaseResponse();
        try {
            SizeResponse sizeResponse = sizeServiceImp.getSizeById(id);
            baseResponse.setMessage("SUCCESS");
            baseResponse.setData(sizeResponse);
            return ResponseEntity.ok(baseResponse);
        }catch (Exception e){
            baseResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(baseResponse);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse> updateColor(@PathVariable int id, @RequestBody SizeRequest sizeRequest){

        BaseResponse baseResponse = new BaseResponse();
        try {
            SizeResponse updatedSize = sizeServiceImp.updateSize(id, sizeRequest);
            baseResponse.setMessage("Size Updated Successfully");
            baseResponse.setData(updatedSize);
            return ResponseEntity.ok(baseResponse);
        }catch (Exception e){
            baseResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(baseResponse);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse> deleteColor(@PathVariable int id){
        BaseResponse baseResponse = new BaseResponse();
        try {
            sizeServiceImp.deleteSize(id);
            baseResponse.setMessage("Size Deleted Successfully");
            return ResponseEntity.ok(baseResponse);
        }catch (Exception e){
            baseResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(baseResponse);
        }
    }
}
