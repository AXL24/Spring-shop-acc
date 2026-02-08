package com.example.demo.controller;


import com.example.demo.model.entity.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
public class UserController {
    @Autowired
    private UserService userService;



    @PostMapping("/add")
    public User addUser(@RequestBody User user){
        return userService.createUser(user);

    }


    @GetMapping("/getAll")
    public Page<User> getAllUser(@RequestParam(value = "page", defaultValue = "1") int page,
                                 @RequestParam(value = "size", defaultValue = "3") int size){
        Pageable pageable = PageRequest.of(page, size);
        return userService.getAllUser(pageable);
    }


}
