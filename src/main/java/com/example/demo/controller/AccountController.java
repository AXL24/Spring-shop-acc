package com.example.demo.controller;

import com.example.demo.dto.request.AccountRequestDTO;
import com.example.demo.dto.response.AccountResponseDTO;
import com.example.demo.model.entity.Account;
import com.example.demo.service.AccountService;
import jakarta.validation.Valid;
<<<<<<< HEAD
=======
import org.modelmapper.ModelMapper;
>>>>>>> c5b3543 (Add ModelMapper configuration and enhance Product and User services with DTOs)
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
<<<<<<< HEAD
    private org.modelmapper.ModelMapper modelMapper;
=======
    private ModelMapper modelMapper;
>>>>>>> c5b3543 (Add ModelMapper configuration and enhance Product and User services with DTOs)

    /**
     * Create a new account (virtual goods).
     * 
     * @param dto account data
     * @return the created account
     */
    @PostMapping
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
    public ResponseEntity<AccountResponseDTO> getAccountById(@PathVariable("id") Long id) {
        Account account = accountService.getAccountById(id);
        AccountResponseDTO response = modelMapper.map(account, AccountResponseDTO.class);
        return ResponseEntity.ok(response);
    }

<<<<<<< HEAD
=======


    @GetMapping("/sold/user/{id}")
    public ResponseEntity<?> getSoldAccountByUserId(@PathVariable("id") Long id) {
        List<Account> accounts = accountService.findSoldAccountsByUserId(id);
        return ResponseEntity.ok(accounts);
    }

>>>>>>> c5b3543 (Add ModelMapper configuration and enhance Product and User services with DTOs)
    /**
     * Get all accounts with pagination.
     * 
     * @param page page number (default: 0)
     * @param size page size (default: 10)
     * @return page of accounts
     */
    @GetMapping("/getAll")
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
    public ResponseEntity<Void> deleteAccount(@PathVariable("id") Long id) {
        accountService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }
}
