package com.example.cozastore.controller;

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

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductServiceImp productServiceImp;

    @GetMapping("")
    public ResponseEntity<?> getProduct(){
        return new ResponseEntity<>("Get Product", HttpStatus.OK);
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

    @GetMapping("/{tenFile}")
    public ResponseEntity<?> downloadProductFile(@PathVariable String tenFile){
        Resource resource = productServiceImp.downloadProductFile(tenFile);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + tenFile + "\"").body(resource);
    }
}
