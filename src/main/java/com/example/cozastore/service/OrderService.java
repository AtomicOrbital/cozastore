package com.example.cozastore.service;

import com.example.cozastore.entity.OrderEntity;
import com.example.cozastore.entity.UserEntity;
import com.example.cozastore.payload.request.OrderRequest;
import com.example.cozastore.payload.response.OrderResponse;
import com.example.cozastore.repository.OrderRepository;
import com.example.cozastore.service.imp.OrderServiceImp;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService implements OrderServiceImp {
    @Autowired
    private OrderRepository orderRepository;

    private Logger logger = LoggerFactory.getLogger(OrderService.class);

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

    @Override
    public boolean insertOrder(OrderRequest orderRequest) {
        boolean isSuccess = false;
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setTotalPrice(orderRequest.getTotalPrice());
        orderEntity.setCreateDate(orderRequest.getCreateDate());
        UserEntity user = new UserEntity();
        user.setId(orderRequest.getIdUser());
        orderEntity.setUser(user);

        try {
            orderRepository.save(orderEntity);
            isSuccess = true;
        } catch (Exception e) {
            logger.info("Unable to insert" + e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
        return isSuccess;
    }
}
