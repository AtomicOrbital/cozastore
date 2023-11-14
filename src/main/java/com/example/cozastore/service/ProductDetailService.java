package com.example.cozastore.service;

import com.example.cozastore.entity.*;
import com.example.cozastore.payload.request.ProductDetailRequest;
import com.example.cozastore.payload.response.ProductDetailResponse;
import com.example.cozastore.payload.response.ProductResponse;
import com.example.cozastore.repository.ProductDetailRepository;
import com.example.cozastore.service.imp.ProductDetailServiceImp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductDetailService implements ProductDetailServiceImp {
    @Autowired
    private ProductDetailRepository productDetailRepository;

    private final Logger logger = LoggerFactory.getLogger(ProductDetailService.class);

    @Override
    public List<ProductDetailResponse> getAllProductDetails() {
        List<ProductDetailResponse> responseList = new ArrayList<>();
        List<ProductDetailEntity> entityList = productDetailRepository.findAll();
        for (ProductDetailEntity entity: entityList) {
            ProductDetailResponse response = new ProductDetailResponse();
            response.setId(entity.getIdProductDetail());
            response.setIdColor(entity.getColor().getId());
            response.setIdProduct(entity.getProduct().getId());
            response.setIdSize(entity.getIdSize().getId());
            response.setQuantity(entity.getQuantity());
            response.setDescription(entity.getDesc());
            responseList.add(response);
        }
        return responseList;
    }

    @Override
    public boolean insertProductDetail(ProductDetailRequest productDetailRequest) {
        boolean isSuccess = false;
        ProductDetailEntity productDetail = new ProductDetailEntity();

        ColorEntity color = new ColorEntity();
        color.setId(productDetailRequest.getIdColor());
        productDetail.setColor(color);

        ProductEntity product = new ProductEntity();
        product.setId(productDetailRequest.getIdProduct());
        productDetail.setProduct(product);

        SizeEntity size = new SizeEntity();
        size.setId(productDetailRequest.getIdSize());
        productDetail.setIdSize(size);

        productDetail.setQuantity(productDetailRequest.getQuantity());
        productDetail.setDesc(productDetailRequest.getDescription());
        try {
            productDetailRepository.save(productDetail);
            isSuccess = true;
        } catch (Exception e) {
            logger.info("Unable to insert product details. Please check your ID. Exception: " + e.getLocalizedMessage());
        }
        return isSuccess;
    }

    @Override
    public ProductDetailResponse getProductDetailById(int id) {
        ProductDetailResponse response = new ProductDetailResponse();
        Optional<ProductDetailEntity> optionalProductDetailEntity = productDetailRepository.findById(id);
        if (optionalProductDetailEntity.isPresent()){
            ProductDetailEntity productDetail = optionalProductDetailEntity.get();
            response.setId(productDetail.getIdProductDetail());
            response.setIdProduct(productDetail.getProduct().getId());
            response.setIdColor(productDetail.getColor().getId());
            response.setIdSize(productDetail.getIdSize().getId());
            response.setQuantity(productDetail.getQuantity());
            response.setDescription(productDetail.getDesc());
        } else {
            logger.info("Can't find the product details. Please check your ID again. ");
        }
        return response;
    }

    @Override
    public boolean deleteProductDetailById(int id) {
        boolean isSuccess = false;
        try {
            productDetailRepository.deleteById(id);
            isSuccess = true;
        } catch (Exception e){
            logger.info("Unable to delete product details from ID. " + e.getLocalizedMessage());
        }
        return isSuccess;
    }

    @Override
    public boolean modifyProductDetailById(int id, ProductDetailRequest productDetailRequest) {
        boolean isSuccess = false;
        Optional<ProductDetailEntity> optionalProductDetailEntity = productDetailRepository.findById(id);
        if (optionalProductDetailEntity.isPresent()){
            ProductDetailEntity productDetail = optionalProductDetailEntity.get();
            ColorEntity color = new ColorEntity();
            color.setId(productDetailRequest.getIdColor());
            productDetail.setColor(color);

            ProductEntity product = new ProductEntity();
            product.setId(productDetailRequest.getIdProduct());
            productDetail.setProduct(product);

            SizeEntity size = new SizeEntity();
            size.setId(productDetailRequest.getIdSize());
            productDetail.setIdSize(size);

            productDetail.setQuantity(productDetailRequest.getQuantity());
            productDetail.setDesc(productDetailRequest.getDescription());
            productDetailRepository.save(productDetail);
            isSuccess = true;
        } else {
            logger.info("Can't find the product details. Please check your ID again. ");
        }
        return isSuccess;
    }
}
