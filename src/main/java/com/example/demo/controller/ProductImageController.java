package com.example.demo.controller;


import com.example.demo.model.entity.ProductImage;
import com.example.demo.service.ProductImageService;
import com.example.demo.service.ProductService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product_img")
public class ProductImageController {

    @Autowired
    private ProductImageService productImageService;

    @GetMapping("/{product_id}")
    public List<ProductImage> getProductImages(@PathVariable("product_id") Long productId) {
        return productImageService.getProductImageByProductId(productId);
    }

    @PostMapping(value = "/{product_id}/uploads", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<ProductImage>> uploadProductImages(
            @PathVariable("product_id") Long productId,
            @RequestPart("files") List<MultipartFile> files) {
        try {
            List<ProductImage> savedImages = productImageService.uploadImages(productId, files);
            return ResponseEntity.ok(savedImages);
        } catch (java.io.IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
