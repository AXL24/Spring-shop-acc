package com.example.demo.repository.mysql;

import com.example.demo.model.entity.Order;
import com.example.demo.model.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for OrderItem entity.
 * Extends JpaRepository to provide CRUD operations and custom query methods.
 */
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    
    /**
     * Find all order items for a specific order.
     * 
     * @param orderId the ID of the order
     * @return list of order items belonging to the order
     */
    List<OrderItem> findByOrderId(Long orderId);

    List<OrderItem> order(Order order);
}
