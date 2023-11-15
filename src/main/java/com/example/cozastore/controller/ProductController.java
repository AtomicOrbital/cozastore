package com.example.cozastore.controller;

import com.example.cozastore.payload.request.ProductRequest;
import com.example.cozastore.payload.response.BaseResponse;
import com.example.cozastore.payload.response.ProductResponse;
import com.example.cozastore.service.ProductService;
import com.example.cozastore.service.imp.FileStorageServiceImp;
import com.example.cozastore.service.imp.ProductServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductServiceImp productServiceImp;

    @GetMapping("")
    public ResponseEntity<?> getProduct(){
        BaseResponse response = new BaseResponse();
        try {
            List<ProductResponse> responseList = productServiceImp.getAllProducts();
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
    public ResponseEntity<?> insertProduct(@RequestParam MultipartFile file,
                                           @RequestParam String title,
                                           @RequestParam double price,
                                           @RequestParam int idCategory,
                                           @RequestParam String tags
    ){
        BaseResponse response = new BaseResponse();
        try {
            boolean isSuccess = productServiceImp.insertProduct(file,title, price, idCategory,tags);
            response.setMessage("Product Created Successfully");
            response.setData(isSuccess);
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("download/{tenFile}")
    public ResponseEntity<?> downloadProductFile(@PathVariable String tenFile){
        Resource resource = productServiceImp.downloadProductFile(tenFile);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + tenFile + "\"").body(resource);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductFromId(@PathVariable int id){
        BaseResponse baseResponse = new BaseResponse();
        try {
            ProductResponse response = productServiceImp.getProductById(id);
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
    public ResponseEntity<?> deleteProductFromId(@PathVariable int id){
        BaseResponse baseResponse = new BaseResponse();
        try {
            boolean isSuccess = productServiceImp.deleteProductById(id);
            baseResponse.setMessage("Deleted Product Successfully");
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
    public ResponseEntity<?> modifyProductFromId(@PathVariable int id, @RequestBody ProductRequest productRequest){
        BaseResponse baseResponse = new BaseResponse();
        try {
            boolean isSuccess = productServiceImp.modifyProductById(id, productRequest);
            baseResponse.setMessage("Updated Product Successfully");
            baseResponse.setData(isSuccess);
            return ResponseEntity.ok(isSuccess);
        }catch (Exception e){
            baseResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(baseResponse);
        }
    }
}
