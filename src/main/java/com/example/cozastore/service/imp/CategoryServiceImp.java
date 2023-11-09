package com.example.cozastore.service.imp;

import com.example.cozastore.payload.request.CategoryRequest;
import com.example.cozastore.payload.request.ColorRequest;
import com.example.cozastore.payload.response.CategoryResponse;
import com.example.cozastore.payload.response.ColorResponse;

import java.util.List;

public interface CategoryServiceImp {
    CategoryResponse createCetegory(CategoryRequest categoryRequest);
    List<CategoryResponse> getAllCategory();
    CategoryResponse getCategoryById(int id);
    CategoryResponse updateCategory(int id,CategoryRequest categoryRequest);
    void deleteCategory(int id);
}
