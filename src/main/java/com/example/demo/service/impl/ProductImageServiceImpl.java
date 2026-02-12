package com.example.demo.service.impl;

import com.example.demo.model.entity.Product;
import com.example.demo.model.entity.ProductImage;
import com.example.demo.repository.mysql.ProductImageRepository;
import com.example.demo.repository.mysql.ProductRepository;
import com.example.demo.service.ProductImageService;

import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductImageServiceImpl implements ProductImageService {
    
    private final ProductImageRepository productImageRepository;
    private final ProductRepository productRepository;
    private final FileStorageService fileStorageService;

    @Value("${app.upload.products-dir}")
    private String productsDir;

    @Override
    public List<ProductImage> getProductImageByProductId(Long id) {
        return productImageRepository.findByProductId(id);
    }

    @Override
    @Transactional
    public List<ProductImage> uploadImages(Long productId, List<MultipartFile> files) throws IOException {
        log.info("Uploading {} images for product ID: {}", files.size(), productId);

        // 1. Find product
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + productId));

        // 2. Check current image count
        List<ProductImage> existingImages = productImageRepository.findByProductId(productId);
        if (existingImages.size() + files.size() > ProductImage.MAX_IMG_PER_PRODUCT) {
            throw new IllegalArgumentException(String.format(
                    "Product can have maximum %d images. Current: %d, New: %d",
                    ProductImage.MAX_IMG_PER_PRODUCT, existingImages.size(), files.size()
            ));
        }

        // 3. Process and save images
        List<ProductImage> savedImages = new ArrayList<>();
        for (MultipartFile file : files) {
            if (file != null && !file.isEmpty()) {
                // Upload to products subdirectory
                String imageUrl = fileStorageService.uploadFile(file, productsDir);

                // Create and save entity
                ProductImage productImage = new ProductImage();
                productImage.setProduct(product);
                productImage.setImageUrl(imageUrl);
                savedImages.add(productImageRepository.save(productImage));
            }
        }

        return savedImages;
    }
}
