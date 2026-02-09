package com.example.demo.repository.mysql;

import com.example.demo.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for Order entity.
 * Extends JpaRepository to provide CRUD operations and custom query methods.
 */
public interface OrderRepository extends JpaRepository<Order, Long> {
    
    /**
     * Find all orders for a specific user.
     * 
     * @param userId the ID of the user
     * @return list of orders belonging to the user
     */
    List<Order> findByUserId(Long userId);
    
    /**
     * Find all orders with a specific status.
     * 
     * @param status the order status (e.g., PENDING, PROCESSING, DELIVERED, CANCELLED)
     * @return list of orders with the specified status
     */
    List<Order> findByStatus(String status);
}
