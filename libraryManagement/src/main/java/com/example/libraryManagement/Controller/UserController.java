package com.example.libraryManagement.Controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.libraryManagement.Entity.User;
import com.example.libraryManagement.Repository.UserRepository;
import com.example.libraryManagement.Service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
     
    @Autowired
    private  UserService userService;


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user) {
            logger.info("Registering the user");
            User savedUser = userService.registerUser(user);
            return ResponseEntity.ok("User registered successfully.");
        }
  

    @GetMapping
    public ResponseEntity<?> getUser(@RequestHeader("username") String username) {
            logger.info("Fetching the user", username);
            User user = userService.getUserByUsername(username);
            return ResponseEntity.ok(user);
    }

    @GetMapping("/isAdmin")
    public ResponseEntity<?> isAdmin(@RequestHeader("username") String username) {
            logger.info("Checking the user role", username);
            boolean admin = userService.isAdmin(username);
            return ResponseEntity.ok("Admin user:" + admin);
    }
}