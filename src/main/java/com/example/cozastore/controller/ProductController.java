package com.example.cozastore.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {

    @GetMapping("")
    public ResponseEntity<?> getProduct(){
        return new ResponseEntity<>("Get Product", HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> insertProduct(){
        return new ResponseEntity<>("Insert Product",HttpStatus.CREATED);
    }
}
