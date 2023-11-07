package com.example.cozastore.controller;

import com.example.cozastore.payload.request.OrderDetailRequest;
import com.example.cozastore.payload.response.OrderDetailResponse;
import com.example.cozastore.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("")
    public ResponseEntity<?> insertOrderDetail(@RequestBody OrderDetailRequest orderDetailRequest){
        boolean isSuccess = orderDetailService.insertOrderDetail(orderDetailRequest);
        return new ResponseEntity<>(isSuccess, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderDetailFromId(@PathVariable int id){
        OrderDetailResponse response = orderDetailService.getOrderDetailById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrderDetailFromId(@PathVariable int id){
        boolean isSuccess = orderDetailService.deleteOrderDetailById(id);
        return new ResponseEntity<>(isSuccess, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> modifyOrderDetailFromId(@PathVariable int id, @RequestBody OrderDetailRequest orderDetailRequest){
        boolean isSuccess = orderDetailService.modifyOrderDetailById(id, orderDetailRequest);
        return new ResponseEntity<>(isSuccess, HttpStatus.OK);
    }
}
