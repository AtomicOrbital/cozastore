package com.example.cozastore.controller;

import com.example.cozastore.payload.request.ProductDetailRequest;
import com.example.cozastore.payload.response.ProductDetailResponse;
import com.example.cozastore.service.ProductDetailService;
import com.example.cozastore.service.imp.ProductDetailServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product-details")
public class ProductDetailController {
    @Autowired
    private ProductDetailServiceImp productDetailServiceImp;

    @GetMapping("")
    public ResponseEntity<?> getProductDetails(){
        List<ProductDetailResponse> responseList = productDetailServiceImp.getAllProductDetails();
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> insertProductDetail(@RequestBody ProductDetailRequest productDetailRequest){
        boolean isSuccess = productDetailServiceImp.insertProductDetail(productDetailRequest);
        return new ResponseEntity<>(isSuccess, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductDetailFromId(@PathVariable int id){
        ProductDetailResponse response = productDetailServiceImp.getProductDetailById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProductDetailFromId(@PathVariable int id){
        boolean isSuccess = productDetailServiceImp.deleteProductDetailById(id);
        return new ResponseEntity<>(isSuccess, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> modifyProductDetailFromId(@PathVariable int id, @RequestBody ProductDetailRequest productDetailRequest){
        boolean isSuccess = productDetailServiceImp.modifyProductDetailById(id, productDetailRequest);
        return new ResponseEntity<>(isSuccess, HttpStatus.OK);
    }
}
