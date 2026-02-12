package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ResourceConfig implements WebMvcConfigurer {

    @Value("${app.upload.categories-dir}")
    private String categoriesDir;

    @Value("${app.upload.products-dir}")
    private String productsDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/uploads/categories/**")
                .addResourceLocations("file:" + categoriesDir + "/");
        registry
                .addResourceHandler("/uploads/products/**")
                .addResourceLocations("file:" + productsDir + "/");

    }
}