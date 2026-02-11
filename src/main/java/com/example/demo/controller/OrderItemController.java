package com.example.demo.controller;


import com.example.demo.dto.request.OrderItemRequestDTO;
import com.example.demo.dto.response.OrderItemResponseDTO;
import com.example.demo.model.entity.OrderItem;
import com.example.demo.service.OrderItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/order-item")
@RequiredArgsConstructor
public class OrderItemController {
    private final OrderItemService orderItemService;
    private final org.modelmapper.ModelMapper modelMapper;

    @PostMapping("")
    public ResponseEntity<OrderItemResponseDTO> createOrderItem(@Valid @RequestBody OrderItemRequestDTO dto) {
        OrderItemResponseDTO response = orderItemService.createOrderItem(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderItemResponseDTO> getOrderItemById(@Valid @PathVariable("id") Long id) {
        OrderItem orderItem = orderItemService.getOrderItem(id);
        OrderItemResponseDTO response = modelMapper.map(orderItem, OrderItemResponseDTO.class);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<OrderItemResponseDTO> updateOrderItem() {
        return ResponseEntity.ok(new OrderItemResponseDTO());
    }

    @GetMapping("order/{orderId}")
    public ResponseEntity<java.util.List<OrderItemResponseDTO>> getOrderItemByOrderId(@Valid @PathVariable("orderId") Long orderId) {
        java.util.List<OrderItem> orderItems = orderItemService.findByOrderId(orderId);
        
        java.util.List<OrderItemResponseDTO> response = orderItems.stream()
            .map(item -> modelMapper.map(item, OrderItemResponseDTO.class))
            .toList();
            
        return ResponseEntity.ok(response);
    }
}
