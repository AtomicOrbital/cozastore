package com.example.cozastore.controller;

import com.example.cozastore.payload.response.OrderDetailResponse;
import com.example.cozastore.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order-details")
public class OrderDetailController {
    @Autowired
    private OrderDetailService orderDetailService;

    @GetMapping("")
    public ResponseEntity<?> getOrderDetails(){
        List<OrderDetailResponse> responseList = orderDetailService.getAllOrderDetails();
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }
}
