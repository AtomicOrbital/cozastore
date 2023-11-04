package com.example.cozastore.service;

import com.example.cozastore.entity.OrderEntity;
import com.example.cozastore.entity.UserEntity;
import com.example.cozastore.payload.request.OrderRequest;
import com.example.cozastore.payload.response.OrderResponse;
import com.example.cozastore.repository.OrderRepository;
import com.example.cozastore.service.imp.OrderServiceImp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService implements OrderServiceImp {
    @Autowired
    private OrderRepository orderRepository;

    private final Logger logger = LoggerFactory.getLogger(OrderService.class);

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
        }
        return isSuccess;
    }

    @Override
    public OrderResponse getOrderById(int id) {
        OrderResponse response = new OrderResponse();
        Optional<OrderEntity> optionalOrderEntity = orderRepository.findById(id);
        if (optionalOrderEntity.isPresent()){
            OrderEntity order = optionalOrderEntity.get();
            response.setId(order.getId());
            response.setTotalPrice(order.getTotalPrice());
            response.setCreateDate(order.getCreateDate());
            response.setIdUser(order.getUser().getId());
        } else {
            logger.error("No entity found, please check your ID again.");
        }
        return response;
    }

    @Override
    public boolean deleteOrderById(int id) {
        boolean isSuccess = false;
        try {
            orderRepository.deleteById(id);
            isSuccess = true;
        } catch (EmptyResultDataAccessException e) {
            logger.error("Entity with ID " + id + " not found");
            logger.error("Data Access Error " + e.getLocalizedMessage());
        }
        return isSuccess;
    }

    @Override
    public boolean modifyOrderById(int id, OrderRequest orderRequest) {
        boolean isSuccess = false;
        Optional<OrderEntity> optionalOrderEntity = orderRepository.findById(id);
        if (optionalOrderEntity.isPresent()){
            OrderEntity order = optionalOrderEntity.get();
            order.setTotalPrice(orderRequest.getTotalPrice());
            order.setCreateDate(orderRequest.getCreateDate());
            UserEntity user = new UserEntity();
            user.setId(orderRequest.getIdUser());
            order.setUser(user);
            orderRepository.save(order);
            isSuccess = true;
        } else {
            logger.error("No entity found, please check your ID again.");
        }
        return isSuccess;
    }
}
