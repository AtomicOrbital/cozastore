package com.example.cozastore.controller;

import com.example.cozastore.payload.request.OrderDetailRequest;
import com.example.cozastore.payload.response.BaseResponse;
import com.example.cozastore.payload.response.OrderDetailResponse;
import com.example.cozastore.service.OrderDetailService;
import com.example.cozastore.service.imp.OrderDetailServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order-details")
public class OrderDetailController {
    @Autowired
    private OrderDetailServiceImp orderDetailServiceImp;

    @GetMapping("")
    public ResponseEntity<?> getOrderDetails(){
        BaseResponse baseResponse = new BaseResponse();
        try {
            List<OrderDetailResponse> responseList = orderDetailServiceImp.getAllOrderDetails();
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
    public ResponseEntity<?> insertOrderDetail(@RequestBody OrderDetailRequest orderDetailRequest){
        BaseResponse baseResponse = new BaseResponse();
        try {
            boolean isSuccess = orderDetailServiceImp.insertOrderDetail(orderDetailRequest);
            baseResponse.setMessage("Created OrderDetail Successfully");
            baseResponse.setData(isSuccess);
            return ResponseEntity.ok(baseResponse);
        }catch (Exception e){
            baseResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(baseResponse);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderDetailFromId(@PathVariable int id){
        BaseResponse baseResponse = new BaseResponse();
        try {
            OrderDetailResponse response = orderDetailServiceImp.getOrderDetailById(id);
            baseResponse.setMessage("SUCCESS");
            baseResponse.setData(response);
            return ResponseEntity.ok(baseResponse);
        }catch (Exception e){
            baseResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(baseResponse);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrderDetailFromId(@PathVariable int id){
        BaseResponse baseResponse = new BaseResponse();
        try {
            boolean isSuccess = orderDetailServiceImp.deleteOrderDetailById(id);
            baseResponse.setMessage("Deleted OrderDetails Successfully");
            baseResponse.setData(isSuccess);
            return ResponseEntity.ok(baseResponse);
        }catch (Exception e){
            baseResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(baseResponse);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> modifyOrderDetailFromId(@PathVariable int id, @RequestBody OrderDetailRequest orderDetailRequest){
        BaseResponse baseResponse = new BaseResponse();
        try {
            boolean isSuccess = orderDetailServiceImp.modifyOrderDetailById(id, orderDetailRequest);
            baseResponse.setMessage("Updated OrderDetails Successfully");
            baseResponse.setData(isSuccess);
            return ResponseEntity.ok(baseResponse);
        }catch (Exception e){
            baseResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(baseResponse);
        }
    }
}
