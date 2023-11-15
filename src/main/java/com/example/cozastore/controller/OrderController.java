package com.example.cozastore.controller;

import com.example.cozastore.payload.request.OrderRequest;
import com.example.cozastore.payload.response.BaseResponse;
import com.example.cozastore.payload.response.OrderResponse;
import com.example.cozastore.service.imp.OrderServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderServiceImp orderServiceImp;

    @GetMapping("")
    public ResponseEntity<?> getOrders(){
        BaseResponse baseResponse = new BaseResponse();
        try {
            List<OrderResponse> responseList = orderServiceImp.getAllOrders();
            baseResponse.setMessage("SUCCESS");
            baseResponse.setData(responseList);
            return ResponseEntity.ok(baseResponse);
        }catch (Exception e){
            baseResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(baseResponse);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("")
    public ResponseEntity<?> insertOrder(@RequestBody OrderRequest orderRequest){
        BaseResponse baseResponse = new BaseResponse();
        try {
            boolean isSuccess = orderServiceImp.insertOrder(orderRequest);
            baseResponse.setMessage("Created Order Successfully");
            baseResponse.setData(isSuccess);
            return ResponseEntity.ok(baseResponse);
        }catch (Exception e){
            baseResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(baseResponse);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderFromId(@PathVariable int id){
        BaseResponse baseResponse = new BaseResponse();
        try {
            OrderResponse response = orderServiceImp.getOrderById(id);
            baseResponse.setMessage("SUCCESS");
            baseResponse.setData(response);
            return ResponseEntity.ok(baseResponse);
        }catch (Exception e){
            baseResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(baseResponse);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> modifyOrderFromId(@PathVariable int id, @RequestBody OrderRequest orderRequest){
        BaseResponse baseResponse = new BaseResponse();
        try {
            boolean isSuccess = orderServiceImp.modifyOrderById(id, orderRequest);
            baseResponse.setMessage("Updated Order Successfully");
            baseResponse.setData(isSuccess);
            return ResponseEntity.ok(baseResponse);
        }catch (Exception e){
            baseResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(baseResponse);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrderFromId(@PathVariable int id){
        BaseResponse baseResponse = new BaseResponse();
        try {
            boolean isSuccess = orderServiceImp.deleteOrderById(id);
            baseResponse.setMessage("Deleted Order Successfully");
            baseResponse.setData(isSuccess);
            return ResponseEntity.ok(isSuccess);
        }catch (Exception e){
            baseResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(baseResponse);
        }
    }
}
