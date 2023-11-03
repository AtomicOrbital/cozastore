package com.example.cozastore.controller;

import com.example.cozastore.payload.request.OrderRequest;
import com.example.cozastore.payload.response.OrderResponse;
import com.example.cozastore.service.imp.OrderServiceImp;
import org.hibernate.criterion.Order;
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

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderFromId(@PathVariable int id){
        OrderResponse response = orderServiceImp.getOrderById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> modifyOrderFromId(@PathVariable int id, @RequestBody OrderRequest orderRequest){
        boolean isSuccess = orderServiceImp.modifyOrderById(id, orderRequest);
        return new ResponseEntity<>(isSuccess, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrderFromId(@PathVariable int id){
        boolean isSuccess = orderServiceImp.deleteOrderById(id);
        return new ResponseEntity<>(isSuccess, HttpStatus.OK);
    }
}
