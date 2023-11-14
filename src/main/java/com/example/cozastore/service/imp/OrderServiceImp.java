package com.example.cozastore.service.imp;

import com.example.cozastore.payload.request.OrderRequest;
import com.example.cozastore.payload.response.OrderResponse;

import java.util.List;

public interface OrderServiceImp {
    List<OrderResponse> getAllOrders();

    boolean insertOrder(OrderRequest orderRequest);

    OrderResponse getOrderById(int id);

    boolean deleteOrderById(int id);

    boolean modifyOrderById(int id, OrderRequest orderRequest);
}
