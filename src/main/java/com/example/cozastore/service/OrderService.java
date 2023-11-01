package com.example.cozastore.service;

import com.example.cozastore.entity.OrderEntity;
import com.example.cozastore.payload.response.OrderResponse;
import com.example.cozastore.repository.OrderRepository;
import com.example.cozastore.service.imp.OrderServiceImp;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService implements OrderServiceImp {
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<OrderResponse> getAllOrders() {
        List<OrderResponse> responseList = new ArrayList<>();
        List<OrderEntity> entityList = orderRepository.findAll();
        for (OrderEntity entity: entityList) {
            OrderResponse response = new OrderResponse();
            response.setId(entity.getId());
            response.setTotalPrice(entity.getTotalPrice());
            response.setCreateDate(entity.getCreateDate());
            response.setIdUser(entity.getUser().getId());
            responseList.add(response);
        }
        return responseList;
    }
}
