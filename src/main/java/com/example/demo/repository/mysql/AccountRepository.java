package com.example.demo.repository.mysql;

import com.example.demo.model.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for Account entity.
 * Accounts represent virtual goods (credentials) to be delivered to customers.
 * Extends JpaRepository to provide CRUD operations and custom query methods.
 */
public interface AccountRepository extends JpaRepository<Account, Long> {
    
    /**
     * Find all accounts for a specific product.
     * 
     * @param productId the ID of the product
     * @return list of accounts for the product
     */
    List<Account> findByProductId(Long productId);
    
    /**
     * Find all accounts with a specific status.
     * 
     * @param status the account status (e.g., AVAILABLE, SOLD, CONTACT)
     * @return list of accounts with the specified status
     */
    List<Account> findByStatus(String status);
    
    /**
     * Find all accounts for a specific product with a specific status.
     * Useful for finding available accounts to deliver for an order.
     * 
     * @param productId the ID of the product
     * @param status the account status
     * @return list of accounts matching both criteria
     */
    List<Account> findByProductIdAndStatus(Long productId, String status);
}
