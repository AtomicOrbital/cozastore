package com.example.cozastore.service;

import com.example.cozastore.entity.OrderDetailEntity;
import com.example.cozastore.payload.request.OrderDetailRequest;
import com.example.cozastore.payload.response.OrderDetailResponse;
import com.example.cozastore.repository.OrderDetailRepository;
import com.example.cozastore.service.imp.OrderDetailServiceImp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderDetailService implements OrderDetailServiceImp {
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    private final Logger logger = LoggerFactory.getLogger(OrderDetailService.class);

    @Override
    public List<OrderDetailResponse> getAllOrderDetails() {
        List<OrderDetailResponse> responseList = new ArrayList<>();
        List<OrderDetailEntity> entityList = orderDetailRepository.findAll();
        for (OrderDetailEntity entity : entityList) {
            OrderDetailResponse response = new OrderDetailResponse();
            response.setId(entity.getId());
            response.setIdProductDetail(entity.getProductDetail().getIdProductDetail());
            response.setIdUser(entity.getUser().getId());
            response.setQuantity(entity.getQuantity());
            response.setCreateDate(entity.getCreateDate());
            response.setPrice(entity.getPrice());
            responseList.add(response);
        }
        return responseList;
    }

    @Override
    public boolean insertOrderDetail(OrderDetailRequest orderDetailRequest) {
        return false;
    }

    @Override
    public OrderDetailResponse getOrderDetailById(int id) {
        return null;
    }

    @Override
    public boolean deleteOrderDetailById(int id) {
        return false;
    }

    @Override
    public boolean modifyOrderDetailById(int id, OrderDetailRequest orderDetailRequest) {
        return false;
    }
}
