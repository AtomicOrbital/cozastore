package com.example.cozastore.controller;

import com.example.cozastore.payload.request.CategoryRequest;
import com.example.cozastore.payload.request.ColorRequest;
import com.example.cozastore.payload.response.BaseResponse;
import com.example.cozastore.payload.response.CategoryResponse;
import com.example.cozastore.payload.response.ColorResponse;
import com.example.cozastore.service.CategoryService;
import com.example.cozastore.service.imp.CategoryServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryServiceImp categoryServiceImp;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<BaseResponse> createCategory(@RequestBody CategoryRequest categoryRequest){
        BaseResponse baseResponse = new BaseResponse();
        try {
            CategoryResponse categoryResponse = categoryServiceImp.createCetegory(categoryRequest);
            baseResponse.setData(categoryResponse);
            baseResponse.setMessage("Category created successfully");
            return ResponseEntity.ok(baseResponse);
        } catch(Exception e){
            baseResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(baseResponse);
        }
    }

    @GetMapping("")
    public ResponseEntity<?> getCategory(){
        List<CategoryResponse> list = categoryServiceImp.getAllCategory();
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(list);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse> getCategoryById(@PathVariable int id){
        BaseResponse baseResponse = new BaseResponse();
        try {
            CategoryResponse categoryResponse = categoryServiceImp.getCategoryById(id);
            baseResponse.setMessage("SUCCESS");
            baseResponse.setData(categoryResponse);
            return ResponseEntity.ok(baseResponse);
        }catch (Exception e){
            baseResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(baseResponse);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse> updateCategory(@PathVariable int id, @RequestBody CategoryRequest categoryRequest){

        BaseResponse baseResponse = new BaseResponse();
        try {
            CategoryResponse updatedCategory = categoryServiceImp.updateCategory(id, categoryRequest);
            baseResponse.setMessage("Category Updated Successfully");
            baseResponse.setData(updatedCategory);
            return ResponseEntity.ok(baseResponse);
        }catch (Exception e){
            baseResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(baseResponse);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse> deleteCategory(@PathVariable int id){
        BaseResponse baseResponse = new BaseResponse();
        try {
            categoryServiceImp.deleteCategory(id);
            baseResponse.setMessage("Category Deleted Successfully");
            return ResponseEntity.ok(baseResponse);
        }catch (Exception e){
            baseResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(baseResponse);
        }
    }
}
