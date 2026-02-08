package com.example.demo.service.impl;

import com.example.demo.dto.request.ProductRequestDTO;
import com.example.demo.model.entity.Category;
import com.example.demo.model.entity.Product;
import com.example.demo.repository.mysql.CategoryRepository;
import com.example.demo.repository.mysql.ProductRepository;
import com.example.demo.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    @Transactional
    public Product createProduct( @RequestBody ProductRequestDTO productRequestDTO) {

        Category existingCategory = categoryRepository.findById(productRequestDTO.getCategory())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Product newProduct = Product.builder()
                .name(productRequestDTO.getName())
                .price(productRequestDTO.getPrice())
                .category(existingCategory)
                .description(productRequestDTO.getDescription())
                .platform(productRequestDTO.getPlatform())
                .stock(productRequestDTO.getStock())
                .active(true)
                .build();
        return productRepository.save(newProduct);
    }

    @Override
    public Page<Product> getAllProduct(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Product not found"));

    }


    }

