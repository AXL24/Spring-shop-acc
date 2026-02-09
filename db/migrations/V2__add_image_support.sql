-- Migration: Add Image Support for Categories and Products
-- Version: V2
-- Description: Adds image_url to categories and creates product_images table

USE mydatabase;

-- Add image URL to categories table
ALTER TABLE categories 
ADD COLUMN image_url VARCHAR(500) NULL 
COMMENT 'Relative path to category image';

-- Create product_images table for multiple product images
CREATE TABLE product_images (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    product_id BIGINT NOT NULL,
    image_url VARCHAR(500) NOT NULL COMMENT 'Relative path to product image',
    
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
) COMMENT='Stores multiple images for products';

-- Index for faster queries by product
CREATE INDEX idx_product_images_product_id ON product_images(product_id);

-- Verification
SELECT 'Migration V2 completed successfully' AS status;
