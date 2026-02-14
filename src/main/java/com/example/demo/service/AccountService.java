package com.example.demo.service;

import com.example.demo.dto.request.AccountRequestDTO;
import com.example.demo.dto.response.AccountResponseDTO;
import com.example.demo.model.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service interface for Account entity operations.
 * Accounts represent virtual goods (credentials) to be delivered to customers.
 */
public interface AccountService {
    
    AccountResponseDTO createAccount(AccountRequestDTO dto);
    
    /**
     * Get an account by its ID.
     * 
     * @param id the account ID
     * @return the account entity
     * @throws com.example.demo.exception.ResourceNotFoundException if account not found
     */
    Account getAccountById(Long id);
    
    /**
     * Get all accounts with pagination.
     * 
     * @param pageable pagination information
     * @return page of account entities
     */
    Page<Account> getAllAccounts(Pageable pageable);
<<<<<<< HEAD
    
=======



    List<Account> findSoldAccountsByUserId( Long userId);
>>>>>>> c5b3543 (Add ModelMapper configuration and enhance Product and User services with DTOs)
    /**
     * Get all accounts for a specific product.
     * 
     * @param productId the product ID
     * @return list of account entities for the product
     */
    List<Account> getAccountsByProduct(Long productId);
    
    /**
     * Get available accounts for a product (status=AVAILABLE).
     * 
     * @param productId the product ID
     * @return list of available account entities
     */
    List<Account> getAvailableAccountsByProduct(Long productId);
    
    AccountResponseDTO updateAccount(Long id, AccountRequestDTO dto);
    
    /**
     * Delete an account.
     * 
     * @param id the account ID
     * @throws com.example.demo.exception.ResourceNotFoundException if account not found
     */
    void deleteAccount(Long id);
}
