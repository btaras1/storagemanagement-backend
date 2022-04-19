package com.management.storage.controller;

import com.management.storage.model.Storage;
import com.management.storage.model.User;
import com.management.storage.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @GetMapping
    List<User> findAll(){return userRepository.findAll();}
}
