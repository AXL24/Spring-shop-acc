package com.example.demo.service.impl;

import com.example.demo.dto.request.OrderItemRequestDTO;
import com.example.demo.dto.request.OrderRequestDTO;
import com.example.demo.dto.response.OrderItemResponseDTO;
import com.example.demo.dto.response.OrderResponseDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.entity.Order;
import com.example.demo.model.entity.OrderItem;
import com.example.demo.model.entity.Product;
import com.example.demo.model.entity.User;
import com.example.demo.repository.mysql.OrderItemRepository;
import com.example.demo.repository.mysql.OrderRepository;
import com.example.demo.repository.mysql.ProductRepository;
import com.example.demo.repository.mysql.UserRepository;
import com.example.demo.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Implementation of OrderService.
 * Handles order creation, status management, and virtual goods delivery logic.
 */
@Service
public class OrderServiceImpl implements OrderService {
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private OrderItemRepository orderItemRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private com.example.demo.repository.mysql.AccountRepository accountRepository;

    @Autowired
    private ModelMapper modelMapper;

    //TODO IMPLEMENT RESERVATION LOGIC FOR PENDING ORDERS
    @Override
    @Transactional
    public OrderResponseDTO createOrder(OrderRequestDTO dto) {
        // Validate user exists
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + dto.getUserId()));


        modelMapper.typeMap(OrderRequestDTO.class, Order.class);
        
        // Create order
        Order order = new Order();
        modelMapper.map(dto, order);
        order.setOrderCode(generateOrderCode());
        order.setUser(user);
        order.setCustomerNote(dto.getCustomerNote());
        order.setCreated(Instant.now());
        order.setUpdated(Instant.now());

        // Create order items and calculate total
        BigDecimal totalAmount = BigDecimal.ZERO;
        List<OrderItem> orderItems = new ArrayList<>();
        
        // Map to keep track of accounts already "claimed" in this transaction
        java.util.Map<Long, Integer> productToClaimedCount = new java.util.HashMap<>();

        for (OrderItemRequestDTO itemDto : dto.getOrderItems()) {
            Product product = productRepository.findById(itemDto.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + itemDto.getProductId()));
            
            // Check available accounts first
            List<com.example.demo.model.entity.Account> availableAccounts = accountRepository.findByProductIdAndStatus(product.getId(), "AVAILABLE");
            int alreadyClaimed = productToClaimedCount.getOrDefault(product.getId(), 0);
            
            if (availableAccounts.size() < itemDto.getQuantity() + alreadyClaimed) {
                throw new IllegalArgumentException("Insufficient available accounts for product: " + product.getName());
            }
            
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(itemDto.getQuantity());
            orderItem.setUnitPrice(product.getPrice());
            
            BigDecimal itemTotal = product.getPrice().multiply(BigDecimal.valueOf(itemDto.getQuantity()));
            orderItem.setTotalPrice(itemTotal);
            totalAmount = totalAmount.add(itemTotal);
            
            orderItems.add(orderItem);
            productToClaimedCount.put(product.getId(), alreadyClaimed + itemDto.getQuantity());
            
            // Update product stock
            product.setStock(product.getStock() - itemDto.getQuantity());
            productRepository.save(product);
        }
        
        order.setTotalAmount(totalAmount);
        order.setOrderItems(orderItems);
        order.setStatus("COMPLETED");
        
        // Save order and items first to generate IDs
        Order savedOrder = orderRepository.saveAndFlush(order);

        // Reset claimed count for assignment phase
        productToClaimedCount.clear();

        // Now link accounts to saved items
        for (OrderItem savedItem : savedOrder.getOrderItems()) {
             List<com.example.demo.model.entity.Account> availableAccounts = 
                 accountRepository.findByProductIdAndStatus(savedItem.getProduct().getId(), "AVAILABLE");
             
             int alreadyClaimed = productToClaimedCount.getOrDefault(savedItem.getProduct().getId(), 0);
             List<com.example.demo.model.entity.Account> assignedAccounts = new ArrayList<>();
             for (int i = 0; i < savedItem.getQuantity(); i++) {
                 com.example.demo.model.entity.Account account = availableAccounts.get(alreadyClaimed + i);
                 account.setOrderItem(savedItem);
                 account.setStatus("SOLD");
                 account.setSold(Instant.now());
                 accountRepository.save(account);
                 assignedAccounts.add(account);
             }
             savedItem.setAccounts(assignedAccounts);
             productToClaimedCount.put(savedItem.getProduct().getId(), alreadyClaimed + savedItem.getQuantity());
        }
        
        Order finalOrder = orderRepository.save(savedOrder);
        OrderResponseDTO response =  modelMapper.map(finalOrder, OrderResponseDTO.class);
        response.setUsername(user.getUsername());
        return response;
    }

    @Override
    @Transactional
    public OrderResponseDTO updateOrderWithDetails(Long id, List<OrderItemRequestDTO> items) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));

        // Only allow updates if status is null (cart/draft state)
        if (order.getStatus() != null) {
            throw new IllegalStateException("Cannot update order that is already placed (status not null)");
        }

        // Handle items and stock
        // 1. Restore stock and accounts for existing items
        if (order.getOrderItems() != null) {
            for (OrderItem existingItem : order.getOrderItems()) {
                Product product = existingItem.getProduct();
                product.setStock(product.getStock() + existingItem.getQuantity());
                productRepository.save(product);
                
                // Restore accounts status
                if (existingItem.getAccounts() != null) {
                    for (com.example.demo.model.entity.Account acc : existingItem.getAccounts()) {
                        acc.setOrderItem(null);
                        acc.setStatus("AVAILABLE");
                        acc.setSold(null);
                        accountRepository.save(acc);
                    }
                }
            }
            order.getOrderItems().clear();
        } else {
            order.setOrderItems(new ArrayList<>());
        }

        // 2. Add new items and deduct stock
        BigDecimal totalAmount = BigDecimal.ZERO;
        List<OrderItem> newOrderItems = new ArrayList<>();
        
        // Map to keep track of accounts already "claimed" in this transaction to avoid double assignment
        java.util.Map<Long, Integer> productToClaimedCount = new java.util.HashMap<>();

        for (OrderItemRequestDTO itemDto : items) {
            Product product = productRepository.findById(itemDto.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + itemDto.getProductId()));
            
            // Check available accounts
            List<com.example.demo.model.entity.Account> availableAccounts = accountRepository.findByProductIdAndStatus(product.getId(), "AVAILABLE");
            int alreadyClaimed = productToClaimedCount.getOrDefault(product.getId(), 0);
            
            if (availableAccounts.size() < itemDto.getQuantity() + alreadyClaimed) {
                throw new IllegalArgumentException("Insufficient available accounts for product: " + product.getName());
            }
            
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(itemDto.getQuantity());
            orderItem.setUnitPrice(product.getPrice());
            
            BigDecimal itemTotal = product.getPrice().multiply(BigDecimal.valueOf(itemDto.getQuantity()));
            orderItem.setTotalPrice(itemTotal);
            totalAmount = totalAmount.add(itemTotal);
            
            newOrderItems.add(orderItem);
            productToClaimedCount.put(product.getId(), alreadyClaimed + itemDto.getQuantity());
            
            product.setStock(product.getStock() - itemDto.getQuantity());
            productRepository.save(product);
        }
        
        order.getOrderItems().addAll(newOrderItems);
        order.setTotalAmount(totalAmount);
        order.setUpdated(Instant.now());
        
        // Save order and items first
        Order savedOrder = orderRepository.saveAndFlush(order);

        // Reset claimed count for assignment phase
        productToClaimedCount.clear();

        // Link accounts to saved items
        for (OrderItem savedItem : savedOrder.getOrderItems()) {
            if (savedItem.getAccounts() == null || savedItem.getAccounts().isEmpty()) {
                List<com.example.demo.model.entity.Account> availableAccounts = 
                        accountRepository.findByProductIdAndStatus(savedItem.getProduct().getId(), "AVAILABLE");
                
                int alreadyClaimed = productToClaimedCount.getOrDefault(savedItem.getProduct().getId(), 0);
                List<com.example.demo.model.entity.Account> assignedAccounts = new ArrayList<>();
                for (int i = 0; i < savedItem.getQuantity(); i++) {
                    com.example.demo.model.entity.Account account = availableAccounts.get(alreadyClaimed + i);
                    account.setOrderItem(savedItem);
                    account.setStatus("SOLD");
                    account.setSold(Instant.now());
                    accountRepository.save(account);
                    assignedAccounts.add(account);
                }
                savedItem.setAccounts(assignedAccounts);
                productToClaimedCount.put(savedItem.getProduct().getId(), alreadyClaimed + savedItem.getQuantity());
            }
        }
        
        Order finalOrder = orderRepository.save(savedOrder);
        OrderResponseDTO response = modelMapper.map(finalOrder, OrderResponseDTO.class);
        response.setUsername(order.getUser().getUsername());
        return response;
    }

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
    }

    @Override
    public Page<Order> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    @Override
    public List<Order> getUserOrders(Long userId) {
        return orderRepository.findByUserId(userId);
    }


//
    @Override
    @Transactional
    public Order updateOrderStatus(Long id, String status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));

        order.setStatus(status);
        order.setUpdated(Instant.now());

        return orderRepository.save(order);
    }

//    @Override
//    @Transactional
//    public OrderResponseDTO updateOrder(Long id, OrderRequestDTO dto) {
//        return updateOrderWithDetails(id, dto.getOrderItems());
//    }




    @Override
    @Transactional
    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        orderRepository.delete(order);
    }


    private String generateOrderCode() {
        String timestamp = String.valueOf(System.currentTimeMillis());
        String random = UUID.randomUUID().toString().substring(0, 4).toUpperCase();
        return timestamp + "." + random;
    }
}
