package com.example.cozastore.service.imp;

import com.example.cozastore.payload.request.OrderDetailRequest;
import com.example.cozastore.payload.response.OrderDetailResponse;

import java.util.List;

public interface OrderDetailServiceImp {
    List<OrderDetailResponse> getAllOrderDetails();

    boolean insertOrderDetail(OrderDetailRequest orderDetailRequest);

    OrderDetailResponse getOrderDetailById(int id);

    boolean deleteOrderDetailById(int id);

    boolean modifyOrderDetailById(int id, OrderDetailRequest orderDetailRequest);

}
