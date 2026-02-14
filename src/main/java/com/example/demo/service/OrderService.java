package com.example.demo.service;

import com.example.demo.dto.request.OrderRequestDTO;
import com.example.demo.dto.response.OrderResponseDTO;
import com.example.demo.model.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service interface for Order entity operations.
 * Provides CRUD operations and business logic for orders in virtual goods delivery.
 */
public interface OrderService {
    
    OrderResponseDTO createOrder(OrderRequestDTO dto);
    
    /**
     * Get an order by its ID.
     * 
     * @param id the order ID
     * @return the order entity
     * @throws com.example.demo.exception.ResourceNotFoundException if order not found
     */
    Order getOrderById(Long id);



    /**
     * Get all orders with pagination.
     *
     * @param pageable pagination information
     * @return page of order entities
     */
    Page<Order> getAllOrders(Pageable pageable);
    
    /**
     * Get all orders for a specific user.
     * 
     * @param userId the user ID
     * @return list of order entities for the user
     */
    List<Order> getUserOrders(Long userId);
    
    /**
     * Update order status.
     * Valid transitions: PENDING -> PROCESSING -> DELIVERED / CANCELLED
     * 
     * @param id the order ID
     * @param status the new status
     * @return the updated order entity
     * @throws com.example.demo.exception.ResourceNotFoundException if order not found
     */

    Order updateOrderStatus(Long id, String status);



    OrderResponseDTO updateOrderWithDetails(Long id, List<com.example.demo.dto.request.OrderItemRequestDTO> items);
    
    /**
     * Delete an order.
     * 
     * @param id the order ID
     * @throws com.example.demo.exception.ResourceNotFoundException if order not found
     */
    void deleteOrder(Long id);
}
