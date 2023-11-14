package com.example.cozastore.repository;

import com.example.cozastore.entity.ProductDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDetailRepository extends JpaRepository<ProductDetailEntity, Integer> {
}
