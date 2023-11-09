package com.example.cozastore.controller;

import com.example.cozastore.payload.request.ColorRequest;
import com.example.cozastore.payload.response.BaseResponse;
import com.example.cozastore.payload.response.ColorResponse;
import com.example.cozastore.service.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/colors")
public class ColorController {
    @Autowired
    private ColorService colorServiceImp;

    @PostMapping
    public ResponseEntity<BaseResponse> createColor(@RequestBody ColorRequest colorRequest){
        BaseResponse baseResponse = new BaseResponse();
        try {
            ColorResponse colorResponse = colorServiceImp.createColor(colorRequest);
            baseResponse.setData(colorResponse);
            baseResponse.setMessage("Color created successfully");
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
            List<ColorResponse> colors = colorServiceImp.getAllColors();
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
    public ResponseEntity<BaseResponse> getColorById(@PathVariable int id){
        BaseResponse baseResponse = new BaseResponse();
        try {
            ColorResponse colorResponse = colorServiceImp.getColorById(id);
            baseResponse.setMessage("SUCCESS");
            baseResponse.setData(colorResponse);
            return ResponseEntity.ok(baseResponse);
        }catch (Exception e){
            baseResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(baseResponse);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse> updateColor(@PathVariable int id, @RequestBody ColorRequest colorRequest){

        BaseResponse baseResponse = new BaseResponse();
        try {
            ColorResponse updatedColor = colorServiceImp.updateColor(id, colorRequest);
            baseResponse.setMessage("Color Updated Successfully");
            baseResponse.setData(updatedColor);
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
            colorServiceImp.deleteColor(id);
            baseResponse.setMessage("Color Deleted Successfully");
            return ResponseEntity.ok(baseResponse);
        }catch (Exception e){
            baseResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(baseResponse);
        }
    }
}
