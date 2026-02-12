package com.example.demo.service;

import com.example.demo.model.entity.ProductImage;

import java.util.List;

public interface ProductImageService {
    List<ProductImage> getProductImageByProductId(Long id);

    List<ProductImage> uploadImages(Long productId, List<org.springframework.web.multipart.MultipartFile> files) throws java.io.IOException;
}
