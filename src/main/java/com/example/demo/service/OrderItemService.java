package com.example.demo.service;

import com.example.demo.dto.request.OrderItemRequestDTO;
import com.example.demo.dto.response.OrderItemResponseDTO;
import com.example.demo.model.entity.OrderItem;
import jakarta.validation.Valid;

import java.util.List;

public interface OrderItemService {

    OrderItemResponseDTO createOrderItem(OrderItemRequestDTO orderItemRequestDTO);

    OrderItem getOrderItem(Long id);

    OrderItemResponseDTO updateOrderItem(Long id, OrderItemRequestDTO orderItemRequestDTO);

    void deleteOrderItem(Long id);

    List<OrderItem> findByOrderId(Long orderId);

}
