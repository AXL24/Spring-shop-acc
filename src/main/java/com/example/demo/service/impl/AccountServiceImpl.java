package com.example.demo.service.impl;

import com.example.demo.dto.request.AccountRequestDTO;
import com.example.demo.dto.response.AccountResponseDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.entity.Account;
import com.example.demo.model.entity.Product;
import com.example.demo.repository.mysql.AccountRepository;
import com.example.demo.repository.mysql.ProductRepository;
import com.example.demo.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    @Transactional
    public AccountResponseDTO createAccount(AccountRequestDTO dto) {
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + dto.getProductId()));
        
        Account account = new Account();
        account.setProduct(product);
        account.setUsername(dto.getUsername());
        account.setPassword(dto.getPassword());
        account.setStatus(dto.getStatus() != null ? dto.getStatus() : "AVAILABLE");
        account.setCreated(Instant.now());
        
        Account savedAccount = accountRepository.save(account);
        return mapToResponseDTO(savedAccount);
    }

    @Override
    public AccountResponseDTO getAccountById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with id: " + id));
        return mapToResponseDTO(account);
    }

    @Override
    public Page<AccountResponseDTO> getAllAccounts(Pageable pageable) {
        return accountRepository.findAll(pageable)
                .map(this::mapToResponseDTO);
    }

    @Override
    public List<AccountResponseDTO> getAccountsByProduct(Long productId) {
        List<Account> accounts = accountRepository.findByProductId(productId);
        return accounts.stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AccountResponseDTO> getAvailableAccountsByProduct(Long productId) {
        List<Account> accounts = accountRepository.findByProductIdAndStatus(productId, "AVAILABLE");
        return accounts.stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AccountResponseDTO updateAccount(Long id, AccountRequestDTO dto) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with id: " + id));
        
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + dto.getProductId()));
        
        account.setProduct(product);
        account.setUsername(dto.getUsername());
        account.setPassword(dto.getPassword());
        if (dto.getStatus() != null) {
            account.setStatus(dto.getStatus());
            if ("SOLD".equals(dto.getStatus())) {
                account.setSold(Instant.now());
            }
        }
        
        Account updatedAccount = accountRepository.save(account);
        return mapToResponseDTO(updatedAccount);
    }

    @Override
    @Transactional
    public void deleteAccount(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with id: " + id));
        accountRepository.delete(account);
    }

    /**
     * Maps Account entity to AccountResponseDTO.
     * 
     * @param account the account entity
     * @return the account response DTO
     */
    private AccountResponseDTO mapToResponseDTO(Account account) {
        return AccountResponseDTO.builder()
                .id(account.getId())
                .productId(account.getProduct().getId())
                .productName(account.getProduct().getName())
                .username(account.getUsername())
                .password(account.getPassword())
                .status(account.getStatus())
                .sold(account.getSold())
                .created(account.getCreated())
                .build();
    }
}
