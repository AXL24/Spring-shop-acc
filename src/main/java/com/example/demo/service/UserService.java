package com.example.demo.service;

import com.example.demo.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    User createUser(User user);
    Page<User> getAllUser(Pageable pageable);


}
