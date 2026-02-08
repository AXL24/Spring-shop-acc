package com.example.demo.controller;


import com.example.demo.dto.request.ProductRequestDTO;
import com.example.demo.model.entity.Product;
import com.example.demo.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("api/v1/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/add")

    public Product addProduct(@Valid @RequestBody ProductRequestDTO productRequestDTO){
        return productService.createProduct(productRequestDTO);
    }

    @GetMapping("findById/{id}")
    public Product getProductById(@PathVariable("id") Long id){
        // Implementation for retrieving a product by its ID would go here
        return productService.getProductById(id); // Placeholder return statement
    }

}
