package com.example.demo.service.impl;

import com.example.demo.dto.request.OrderItemRequestDTO;
import com.example.demo.dto.response.OrderItemResponseDTO;
import com.example.demo.dto.response.ProductResponseDTO;
import com.example.demo.model.entity.Order;
import com.example.demo.model.entity.OrderItem;
import com.example.demo.model.entity.Product;
import com.example.demo.repository.mysql.OrderItemRepository;
import com.example.demo.repository.mysql.OrderRepository;
import com.example.demo.repository.mysql.ProductRepository;
import com.example.demo.service.OrderItemService;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public OrderItemResponseDTO createOrderItem(OrderItemRequestDTO orderItemRequestDTO) {
        Order order = orderRepository.findById(orderItemRequestDTO.getOrderId()).orElseThrow(
                ()-> new RuntimeException("Order not found"));
        Product product = productRepository.findById(orderItemRequestDTO.getProductId()).orElseThrow(
                ()-> new RuntimeException("Product not found"));
        OrderItem orderItem = OrderItem.builder().
                order(order).
                product(product).
                quantity(orderItemRequestDTO.getQuantity()).
                unitPrice(BigDecimal.valueOf(orderItemRequestDTO.getPrice())).
                totalPrice(BigDecimal.valueOf(orderItemRequestDTO.getTotal())).
                build();
        OrderItem savedItem = orderItemRepository.save(orderItem);
        return modelMapper.map(savedItem, OrderItemResponseDTO.class);
    }

    @Override
    public OrderItem getOrderItem(Long id) {
        return orderItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("OrderItem not found"));
    }

    @Override
    public OrderItemResponseDTO updateOrderItem(Long id, OrderItemRequestDTO dto) {
        OrderItem orderItem = orderItemRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("OrderItem not found"));
        Order order = orderRepository.findById(dto.getOrderId()).orElseThrow(
                ()-> new RuntimeException("Order not found"));
        Product product = productRepository.findById(dto.getProductId()).orElseThrow(
                ()-> new RuntimeException("Product not found"));
        orderItem.setOrder(order);
        orderItem.setProduct(product);
        orderItem.setQuantity(dto.getQuantity());
        orderItem.setUnitPrice(BigDecimal.valueOf(dto.getPrice()));
        orderItem.setTotalPrice(BigDecimal.valueOf(dto.getTotal()));
        
        OrderItem savedItem = orderItemRepository.save(orderItem);
        return modelMapper.map(savedItem, OrderItemResponseDTO.class);
    }

    @Override
    public void deleteOrderItem(Long id) {
        orderItemRepository.deleteById(id);
    }

    @Override
    public List<OrderItem> findByOrderId(Long orderId) {
        // This is handled via the Order aggregate, but if needed:

        return orderItemRepository.findByOrderId(orderId);
    }


}
