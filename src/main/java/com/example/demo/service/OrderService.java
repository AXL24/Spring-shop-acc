package com.example.demo.service;

import com.example.demo.dto.request.OrderRequestDTO;
import com.example.demo.dto.response.OrderResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service interface for Order entity operations.
 * Provides CRUD operations and business logic for orders in virtual goods delivery.
 */
public interface OrderService {
    
    /**
     * Create a new order with order items.
     * Automatically calculates total amount and generates order code.
     * 
     * @param dto the order data with items
     * @return the created order as OrderResponseDTO
     * @throws com.example.demo.exception.ResourceNotFoundException if user or products not found
     * @throws IllegalArgumentException if insufficient stock
     */
    OrderResponseDTO createOrder(OrderRequestDTO dto);
    
    /**
     * Get an order by its ID.
     * 
     * @param id the order ID
     * @return the order as OrderResponseDTO
     * @throws com.example.demo.exception.ResourceNotFoundException if order not found
     */
    OrderResponseDTO getOrderById(Long id);
    
    /**
     * Get all orders with pagination.
     * 
     * @param pageable pagination information
     * @return page of orders
     */
    Page<OrderResponseDTO> getAllOrders(Pageable pageable);
    
    /**
     * Get all orders for a specific user.
     * 
     * @param userId the user ID
     * @return list of orders for the user
     */
    List<OrderResponseDTO> getUserOrders(Long userId);
    
    /**
     * Update order status.
     * Valid transitions: PENDING -> PROCESSING -> DELIVERED / CANCELLED
     * 
     * @param id the order ID
     * @param status the new status
     * @return the updated order
     * @throws com.example.demo.exception.ResourceNotFoundException if order not found
     */
    OrderResponseDTO updateOrderStatus(Long id, String status);
    
    /**
     * Delete an order.
     * 
     * @param id the order ID
     * @throws com.example.demo.exception.ResourceNotFoundException if order not found
     */
    void deleteOrder(Long id);
}
