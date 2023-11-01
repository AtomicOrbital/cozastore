package com.example.cozastore.controller;

import com.example.cozastore.payload.response.OrderResponse;
import com.example.cozastore.service.imp.OrderServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderServiceImp orderServiceImp;

    @GetMapping("")
    public ResponseEntity<?> getOrders(){
        List<OrderResponse> responseList = orderServiceImp.getAllOrders();
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }
}
