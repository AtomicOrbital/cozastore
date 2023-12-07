package com.example.cozastore.controller;

import com.example.cozastore.payload.request.ProductDetailRequest;
import com.example.cozastore.payload.response.BaseResponse;
import com.example.cozastore.payload.response.ProductDetailResponse;
import com.example.cozastore.payload.response.ProductResponse;
import com.example.cozastore.service.ProductDetailService;
import com.example.cozastore.service.imp.ProductDetailServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/product-details")
public class ProductDetailController {
    @Autowired
    private ProductDetailServiceImp productDetailServiceImp;

    @GetMapping("")
    public ResponseEntity<?> getProductDetails(){
        BaseResponse response = new BaseResponse();
        try {
            List<ProductDetailResponse> responseList = productDetailServiceImp.getAllProductDetails();
            response.setMessage("SUCCESS");
            response.setData(responseList);
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("")
    public ResponseEntity<?> insertProductDetail(@RequestBody ProductDetailRequest productDetailRequest){
        BaseResponse response = new BaseResponse();
        try {
            boolean isSuccess = productDetailServiceImp.insertProductDetail(productDetailRequest);
            response.setMessage("Created ProductDetails Successfully");
            response.setData(isSuccess);
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductDetailFromId(@PathVariable int id){
        BaseResponse baseResponse = new BaseResponse();
        try {
            ProductDetailResponse response = productDetailServiceImp.getProductDetailById(id);
            baseResponse.setMessage("SUCCESS");
            baseResponse.setData(response);
            return ResponseEntity.ok(response);
        }catch (Exception e){
            baseResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(baseResponse);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProductDetailFromId(@PathVariable int id){
        BaseResponse baseResponse = new BaseResponse();
        try {
            boolean isSuccess = productDetailServiceImp.deleteProductDetailById(id);
            baseResponse.setMessage("Deleted ProductDetails Successfully");
            baseResponse.setData(isSuccess);
            return ResponseEntity.ok(isSuccess);
        }catch (Exception e){
            baseResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(baseResponse);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> modifyProductDetailFromId(@PathVariable int id, @RequestBody ProductDetailRequest productDetailRequest){
        BaseResponse baseResponse = new BaseResponse();
        try {
            boolean isSuccess = productDetailServiceImp.modifyProductDetailById(id, productDetailRequest);
            baseResponse.setMessage("Updated ProductDetails Successfully");
            baseResponse.setData(isSuccess);
            return ResponseEntity.ok(isSuccess);
        }catch (Exception e){
            baseResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(baseResponse);
        }
    }
}
