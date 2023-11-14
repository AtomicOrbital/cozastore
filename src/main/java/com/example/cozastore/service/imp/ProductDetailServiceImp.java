package com.example.cozastore.service.imp;

import com.example.cozastore.payload.request.ProductDetailRequest;
import com.example.cozastore.payload.response.ProductDetailResponse;
import com.example.cozastore.payload.response.ProductResponse;

import java.util.List;

public interface ProductDetailServiceImp {
    List<ProductDetailResponse> getAllProductDetails();

    boolean insertProductDetail(ProductDetailRequest productDetailRequest);

    ProductDetailResponse getProductDetailById(int id);

    boolean deleteProductDetailById(int id);

    boolean modifyProductDetailById(int id, ProductDetailRequest productDetailRequest);
}
