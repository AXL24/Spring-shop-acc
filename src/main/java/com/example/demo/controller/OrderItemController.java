package com.example.demo.controller;


import com.example.demo.dto.request.OrderItemRequestDTO;
import com.example.demo.dto.response.OrderItemResponseDTO;
import com.example.demo.model.entity.OrderItem;
import com.example.demo.service.OrderItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/order-item")
@RequiredArgsConstructor
public class OrderItemController {
    @Autowired
    private final OrderItemService orderItemService;
    @Autowired
    private final ModelMapper modelMapper;

    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<OrderItemResponseDTO> createOrderItem(@Valid @RequestBody OrderItemRequestDTO dto) {
        OrderItemResponseDTO response = orderItemService.createOrderItem(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<OrderItemResponseDTO> getOrderItemById(@Valid @PathVariable("id") Long id) {
        OrderItem orderItem = orderItemService.getOrderItem(id);
        OrderItemResponseDTO response = modelMapper.map(orderItem, OrderItemResponseDTO.class);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<OrderItemResponseDTO> updateOrderItem() {
        return ResponseEntity.ok(new OrderItemResponseDTO());
    }

    @GetMapping("order/{orderId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<java.util.List<OrderItemResponseDTO>> getOrderItemByOrderId(@Valid @PathVariable("orderId") Long orderId) {
        java.util.List<OrderItem> orderItems = orderItemService.findByOrderId(orderId);
        
        java.util.List<OrderItemResponseDTO> response = orderItems.stream()
            .map(item -> modelMapper.map(item, OrderItemResponseDTO.class))
            .toList();
            
        return ResponseEntity.ok(response);
    }
}
