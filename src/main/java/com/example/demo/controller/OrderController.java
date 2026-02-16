package com.example.demo.controller;

import com.example.demo.dto.request.OrderItemRequestDTO;
import com.example.demo.dto.request.OrderRequestDTO;
import com.example.demo.dto.response.OrderResponseDTO;
import com.example.demo.model.entity.Order;
import com.example.demo.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST controller for Order operations.
 * Provides endpoints for order management in virtual goods delivery system.
 */
@RestController
@RequestMapping("api/v1/order")
public class  OrderController {
    
    @Autowired
    private OrderService orderService;

    @Autowired
    private org.modelmapper.ModelMapper modelMapper;

    /**
     * Create a new order.
     * 
     * @param dto order data with items
     * @return the created order
     */
    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@Valid @RequestBody OrderRequestDTO dto) {
        OrderResponseDTO response = orderService.createOrder(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Get an order by ID.
     * 
     * @param id the order ID
     * @return the order
     */
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable("id") Long id) {
        Order order = orderService.getOrderById(id);
        OrderResponseDTO response = modelMapper.map(order, OrderResponseDTO.class);
        response.setUserId(order.getUser().getId());
        response.setUsername(order.getUser().getUsername());
        response.setCustomerNote(order.getCustomerNote());
        return ResponseEntity.ok(response);
    }

    /**
     * Get all orders with pagination.
     * 
     * @param page page number (default: 0)
     * @param size page size (default: 10)
     * @return page of orders
     */
    @GetMapping("/getAll")
    public ResponseEntity<Page<OrderResponseDTO>> getAllOrders(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Order> orders = orderService.getAllOrders(pageable);
        Page<OrderResponseDTO> response = orders.map(order -> modelMapper.map(order, OrderResponseDTO.class));
        return ResponseEntity.ok(response);
    }

    /**
     * Get all orders for a specific user.
     * 
     * @param userId the user ID
     * @return list of user's orders
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderResponseDTO>> getUserOrders(@PathVariable("userId") Long userId) {
        List<Order> orders = orderService.getUserOrders(userId);
        List<OrderResponseDTO> response = orders.stream()
                .map(order -> modelMapper.map(order, OrderResponseDTO.class))
                .toList();
        return ResponseEntity.ok(response);
    }

    /**
     * Update order status.
     * 
     * @param id the order ID
     * @param statusUpdate map containing the new status
     * @return the updated order
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<OrderResponseDTO> updateOrderStatus(
            @PathVariable("id") Long id,
            @RequestBody Map<String, String> statusUpdate) {
        String status = statusUpdate.get("status");
        if (status == null || status.isEmpty()) {
            throw new IllegalArgumentException("Status is required");
        }
        Order updatedOrder = orderService.updateOrderStatus(id, status);
        OrderResponseDTO response = modelMapper.map(updatedOrder, OrderResponseDTO.class);
        return ResponseEntity.ok(response);
    }

    /**
     * Update order items.
     * Only orders with null status (draft/cart) can be updated.
     * 
     * @param id the order ID
     * @param items list of updated items
     * @return the updated order
     */
    @PatchMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> updateOrderDetail(
            @PathVariable("id") Long id,
            @Valid @RequestBody List<OrderItemRequestDTO> items) {
        OrderResponseDTO response = orderService.updateOrderWithDetails(id, items);
        return ResponseEntity.ok(response);
    }

    /**
     * Delete an order.
     * 
     * @param id the order ID
     * @return no content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable("id") Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}
