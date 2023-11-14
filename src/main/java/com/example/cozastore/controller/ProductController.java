package com.example.cozastore.controller;

import com.example.cozastore.payload.request.ProductRequest;
import com.example.cozastore.payload.response.ProductResponse;
import com.example.cozastore.service.ProductService;
import com.example.cozastore.service.imp.FileStorageServiceImp;
import com.example.cozastore.service.imp.ProductServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        List<ProductResponse> responseList = productServiceImp.getAllProducts();
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> insertProduct(@RequestParam MultipartFile file,
                                           @RequestParam String title,
                                           @RequestParam double price,
                                           @RequestParam int idCategory
    ){
        productServiceImp.insertProduct(file,title, price, idCategory);
        return new ResponseEntity<>("Insert Product",HttpStatus.CREATED);
    }

    @GetMapping("download/{tenFile}")
    public ResponseEntity<?> downloadProductFile(@PathVariable String tenFile){
        Resource resource = productServiceImp.downloadProductFile(tenFile);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + tenFile + "\"").body(resource);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductFromId(@PathVariable int id){
        ProductResponse response = productServiceImp.getProductById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProductFromId(@PathVariable int id){
        boolean isSuccess = productServiceImp.deleteProductById(id);
        return new ResponseEntity<>(isSuccess, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> modifyProductFromId(@PathVariable int id, @RequestBody ProductRequest productRequest){
        boolean isSuccess = productServiceImp.modifyProductById(id, productRequest);
        return new ResponseEntity<>(isSuccess, HttpStatus.OK);
    }
}
