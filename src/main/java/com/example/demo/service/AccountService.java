package com.example.demo.service;

import com.example.demo.dto.request.AccountRequestDTO;
import com.example.demo.dto.response.AccountResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service interface for Account entity operations.
 * Accounts represent virtual goods (credentials) to be delivered to customers.
 */
public interface AccountService {
    
    /**
     * Create a new account (virtual goods).
     * 
     * @param dto the account data
     * @return the created account as AccountResponseDTO
     * @throws com.example.demo.exception.ResourceNotFoundException if product not found
     */
    AccountResponseDTO createAccount(AccountRequestDTO dto);
    
    /**
     * Get an account by its ID.
     * 
     * @param id the account ID
     * @return the account as AccountResponseDTO
     * @throws com.example.demo.exception.ResourceNotFoundException if account not found
     */
    AccountResponseDTO getAccountById(Long id);
    
    /**
     * Get all accounts with pagination.
     * 
     * @param pageable pagination information
     * @return page of accounts
     */
    Page<AccountResponseDTO> getAllAccounts(Pageable pageable);
    
    /**
     * Get all accounts for a specific product.
     * 
     * @param productId the product ID
     * @return list of accounts for the product
     */
    List<AccountResponseDTO> getAccountsByProduct(Long productId);
    
    /**
     * Get available accounts for a product (status=AVAILABLE).
     * 
     * @param productId the product ID
     * @return list of available accounts
     */
    List<AccountResponseDTO> getAvailableAccountsByProduct(Long productId);
    
    /**
     * Update an existing account.
     * 
     * @param id the account ID
     * @param dto the updated account data
     * @return the updated account as AccountResponseDTO
     * @throws com.example.demo.exception.ResourceNotFoundException if account not found
     */
    AccountResponseDTO updateAccount(Long id, AccountRequestDTO dto);
    
    /**
     * Delete an account.
     * 
     * @param id the account ID
     * @throws com.example.demo.exception.ResourceNotFoundException if account not found
     */
    void deleteAccount(Long id);
}
