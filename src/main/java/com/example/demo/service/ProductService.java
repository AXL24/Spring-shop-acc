package com.example.demo.service;

import com.example.demo.dto.request.ProductRequestDTO;
import com.example.demo.model.entity.Product;
import com.example.demo.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    Product createProduct(ProductRequestDTO productRequestDTO);
    Page<Product> getAllProduct(Pageable pageable);
    Product getProductById(Long id);

}
