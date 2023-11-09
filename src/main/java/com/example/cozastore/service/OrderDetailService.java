package com.example.cozastore.service;

import com.example.cozastore.entity.OrderDetailEntity;
import com.example.cozastore.entity.ProductDetailEntity;
import com.example.cozastore.entity.UserEntity;
import com.example.cozastore.payload.request.OrderDetailRequest;
import com.example.cozastore.payload.response.OrderDetailResponse;
import com.example.cozastore.repository.OrderDetailRepository;
import com.example.cozastore.service.imp.OrderDetailServiceImp;
import org.aspectj.weaver.ast.Or;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        boolean isSuccess = false;
        OrderDetailEntity orderDetail = new OrderDetailEntity();
        ProductDetailEntity productDetail = new ProductDetailEntity();
        productDetail.setIdProductDetail(orderDetailRequest.getIdProductDetail());
        orderDetail.setProductDetail(productDetail);
        UserEntity user = new UserEntity();
        user.setId(orderDetailRequest.getIdUser());
        orderDetail.setUser(user);
        orderDetail.setCreateDate(orderDetailRequest.getCreateDate());
        orderDetail.setPrice(orderDetailRequest.getPrice());
        orderDetail.setQuantity(orderDetailRequest.getQuantity());
        try {
            orderDetailRepository.save(orderDetail);
            isSuccess = true;
        } catch (Exception e) {
            logger.info("Unable to insert order details. Please check your ID. Exception: " + e.getLocalizedMessage());
        }
        return isSuccess;
    }

    @Override
    public OrderDetailResponse getOrderDetailById(int id) {
        OrderDetailResponse response = new OrderDetailResponse();
        Optional<OrderDetailEntity> optionalOrderDetailEntity = orderDetailRepository.findById(id);
        if (optionalOrderDetailEntity.isPresent()){
            OrderDetailEntity orderDetail = optionalOrderDetailEntity.get();
            response.setId(orderDetail.getId());
            response.setIdProductDetail(orderDetail.getProductDetail().getIdProductDetail());
            response.setIdUser(orderDetail.getUser().getId());
            response.setPrice(orderDetail.getPrice());
            response.setQuantity(orderDetail.getQuantity());
            response.setCreateDate(orderDetail.getCreateDate());
        } else {
            logger.info("Can't find the order details. Please check your ID again. ");
        }
        return response;
    }

    @Override
    public boolean deleteOrderDetailById(int id) {
        boolean isSuccess = false;
        try {
            orderDetailRepository.deleteById(id);
            isSuccess = true;
        } catch (Exception e){
            logger.info("Unable to delete order details from ID. " +e.getLocalizedMessage());
        }
        return isSuccess;
    }

    @Override
    public boolean modifyOrderDetailById(int id, OrderDetailRequest orderDetailRequest) {
        boolean isSuccess = false;
        Optional<OrderDetailEntity> optionalOrderDetailEntity = orderDetailRepository.findById(id);
        if (optionalOrderDetailEntity.isPresent()){
            OrderDetailEntity orderDetail = optionalOrderDetailEntity.get();
            ProductDetailEntity productDetail = new ProductDetailEntity();
            productDetail.setIdProductDetail(orderDetailRequest.getIdProductDetail());
            orderDetail.setProductDetail(productDetail);
            UserEntity user = new UserEntity();
            user.setId(orderDetailRequest.getIdUser());
            orderDetail.setUser(user);
            orderDetail.setCreateDate(orderDetailRequest.getCreateDate());
            orderDetail.setPrice(orderDetailRequest.getPrice());
            orderDetail.setQuantity(orderDetailRequest.getQuantity());
            orderDetailRepository.save(orderDetail);
            isSuccess = true;
        } else {
            logger.info("Can't find the order details. Please check your ID again. ");
        }
        return isSuccess;
    }
}
