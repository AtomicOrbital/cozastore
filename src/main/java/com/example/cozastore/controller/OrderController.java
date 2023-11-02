package com.example.cozastore.controller;

import com.example.cozastore.payload.request.OrderRequest;
import com.example.cozastore.payload.response.OrderResponse;
import com.example.cozastore.service.imp.OrderServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("")
    public ResponseEntity<?> insertOrder(@RequestBody OrderRequest orderRequest){
        boolean isSuccess = orderServiceImp.insertOrder(orderRequest);
        return new ResponseEntity<>(isSuccess, HttpStatus.OK);
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<?> getOrderFromId(){
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/orders/{id}")
    public ResponseEntity<?> modifyOrderFromId(){
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteOrderFromId(){
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
