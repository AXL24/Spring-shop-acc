package com.example.demo.repository.mysql;

import com.example.demo.model.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Token entity.
 * Tokens are used for authentication and authorization.
 * Extends JpaRepository to provide CRUD operations and custom query methods.
 */
public interface TokenRepository extends JpaRepository<Token, Long> {
    
    /**
     * Find a token by its token string.
     * 
     * @param token the token string
     * @return Optional containing the token if found
     */
    Optional<Token> findByToken(String token);
    
    /**
     * Find all tokens for a specific user.
     * 
     * @param userId the ID of the user
     * @return list of tokens belonging to the user
     */
    List<Token> findByUserId(Long userId);
    
    /**
     * Find all active (non-revoked) tokens for a specific user.
     * 
     * @param userId the ID of the user
     * @param revoked the revoked status (false for active tokens)
     * @return list of active tokens belonging to the user
     */
    List<Token> findByUserIdAndRevoked(Long userId, Boolean revoked);
}
