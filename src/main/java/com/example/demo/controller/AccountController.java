package com.example.demo.controller;

import com.example.demo.dto.request.AccountRequestDTO;
import com.example.demo.dto.response.AccountResponseDTO;
import com.example.demo.model.entity.Account;
import com.example.demo.service.AccountService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for Account (virtual goods) operations.
 * Provides endpoints for managing virtual goods credentials.
 */
@RestController
@RequestMapping("api/v1/account")
public class AccountController {
    
    @Autowired
    private AccountService accountService;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Create a new account (virtual goods).
     * 
     * @param dto account data
     * @return the created account
     */
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<AccountResponseDTO> createAccount(@Valid @RequestBody AccountRequestDTO dto) {
        AccountResponseDTO response = accountService.createAccount(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Get an account by ID.
     * 
     * @param id the account ID
     * @return the account
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<AccountResponseDTO> getAccountById(@PathVariable("id") Long id) {
        Account account = accountService.getAccountById(id);
        AccountResponseDTO response = modelMapper.map(account, AccountResponseDTO.class);
        return ResponseEntity.ok(response);
    }



    @GetMapping("/sold/user/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<?> getSoldAccountByUserId(@PathVariable("id") Long id) {
        List<Account> accounts = accountService.findSoldAccountsByUserId(id);
        return ResponseEntity.ok(accounts);
    }

    /**
     * Get all accounts with pagination.
     * 
     * @param page page number (default: 0)
     * @param size page size (default: 10)
     * @return page of accounts
     */
    @GetMapping("/getAll")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Page<AccountResponseDTO>> getAllAccounts(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Account> accounts = accountService.getAllAccounts(pageable);
        Page<AccountResponseDTO> response = accounts.map(account -> modelMapper.map(account, AccountResponseDTO.class));
        return ResponseEntity.ok(response);
    }

    /**
     * Get all accounts for a specific product.
     * 
     * @param productId the product ID
     * @return list of accounts for the product
     */
    @GetMapping("/product/{productId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<AccountResponseDTO>> getAccountsByProduct(@PathVariable("productId") Long productId) {
        List<Account> accounts = accountService.getAccountsByProduct(productId);
        List<AccountResponseDTO> response = accounts.stream()
                .map(account -> modelMapper.map(account, AccountResponseDTO.class))
                .toList();
        return ResponseEntity.ok(response);
    }

    /**
     * Get available accounts for a product.
     * 
     * @param productId the product ID
     * @return list of available accounts
     */
    @GetMapping("/product/{productId}/available")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<AccountResponseDTO>> getAvailableAccountsByProduct(@PathVariable("productId") Long productId) {
        List<Account> accounts = accountService.getAvailableAccountsByProduct(productId);
        List<AccountResponseDTO> response = accounts.stream()
                .map(account -> modelMapper.map(account, AccountResponseDTO.class))
                .toList();
        return ResponseEntity.ok(response);
    }

    /**
     * Update an account.
     * 
     * @param id the account ID
     * @param dto updated account data
     * @return the updated account
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<AccountResponseDTO> updateAccount(
            @PathVariable("id") Long id,
            @Valid @RequestBody AccountRequestDTO dto) {
        AccountResponseDTO response = accountService.updateAccount(id, dto);
        return ResponseEntity.ok(response);
    }

    /**
     * Delete an account.
     * 
     * @param id the account ID
     * @return no content
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteAccount(@PathVariable("id") Long id) {
        accountService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }
}
