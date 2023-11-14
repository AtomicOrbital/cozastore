package com.example.cozastore.service.imp;

import com.example.cozastore.payload.request.ProductRequest;
import com.example.cozastore.payload.response.ProductResponse;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductServiceImp {
    List<ProductResponse> getAllProducts();
    boolean insertProduct(MultipartFile file,String title,double price, int idCategory);

    Resource downloadProductFile(String tenFile);

    ProductResponse getProductById(int id);

    boolean deleteProductById(int id);

    boolean modifyProductById(int id, ProductRequest productRequest);

}
