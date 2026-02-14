package com.example.demo.service.impl;

import com.example.demo.dto.request.AccountRequestDTO;
import com.example.demo.dto.response.AccountResponseDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.entity.Account;
import com.example.demo.model.entity.Product;
import com.example.demo.repository.mysql.AccountRepository;
import com.example.demo.repository.mysql.ProductRepository;
import com.example.demo.service.AccountService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of AccountService.
 * Handles virtual goods (accounts/credentials) management.
 */
@Service
public class AccountServiceImpl implements AccountService {
    
    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public AccountResponseDTO createAccount(AccountRequestDTO dto) {
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + dto.getProductId()));
        
        Account account = modelMapper.map(dto, Account.class);
        account.setProduct(product);
//        account.setStatus(dto.getStatus() != null ? dto.getStatus() : "AVAILABLE");
        account.setStatus("SOLD");
        account.setCreated(Instant.now());
        
        Account savedAccount = accountRepository.save(account);
        return modelMapper.map(savedAccount, AccountResponseDTO.class);
    }

    @Override
    public Account getAccountById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with id: " + id));
    }

    @Override
    public Page<Account> getAllAccounts(Pageable pageable) {
        return accountRepository.findAll(pageable);
    }

    @Override
    public List<Account> findSoldAccountsByUserId(Long userId) {
        return accountRepository.findSoldAccountsByUserId(userId);
    }

    @Override
    public List<Account> getAccountsByProduct(Long productId) {
        return accountRepository.findByProductId(productId);
    }

    @Override
    public List<Account> getAvailableAccountsByProduct(Long productId) {
        return accountRepository.findByProductIdAndStatus(productId, "AVAILABLE");
    }

    @Override
    @Transactional
    public AccountResponseDTO updateAccount(Long id, AccountRequestDTO dto) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with id: " + id));
        
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + dto.getProductId()));
        
        // Map DTO to existing entity
        modelMapper.map(dto, account);
        
        account.setProduct(product);
        if (dto.getStatus() != null) {
            account.setStatus(dto.getStatus());
            if ("SOLD".equals(dto.getStatus())) {
                account.setSold(Instant.now());
            }
        }
        
        Account savedAccount = accountRepository.save(account);
        return modelMapper.map(savedAccount, AccountResponseDTO.class);
    }

    @Override
    @Transactional
    public void deleteAccount(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with id: " + id));
        accountRepository.delete(account);
    }
}
