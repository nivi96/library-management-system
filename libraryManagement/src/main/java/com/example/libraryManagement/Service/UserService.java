package com.example.libraryManagement.Service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.libraryManagement.Entity.User;
import com.example.libraryManagement.Exception.UserAlreadyExistsException;
import com.example.libraryManagement.Exception.UserNotFoundException;
import com.example.libraryManagement.Repository.UserRepository;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

	@Service
	public class UserService {

		private static final Logger logger = LoggerFactory.getLogger(UserService.class);
        
		@Autowired
	    private UserRepository userRepository;
		@Autowired
	    private PasswordEncoder passwordEncoder;

	   

	    public User registerUser(User user) {
	        logger.info("Registering user: {}", user.getUsername());

	        if (userRepository.findByUsername(user.getUsername()) != null) {
	            throw new UserAlreadyExistsException("Username already exists: " + user.getUsername());
	        }

	        if (!"ADMIN".equalsIgnoreCase(user.getRole()) && !"USER".equalsIgnoreCase(user.getRole())) {
	            throw new RuntimeException("Invalid role. Must be ADMIN or USER");
	        }

	        user.setPassword(passwordEncoder.encode(user.getPassword()));
	        return userRepository.save(user);
	    }

	    public User getUserByUsername(String username) {
	    	 logger.info("Fetching user :", username);
	    	User user = userRepository.findByUsername(username);
	        if (user == null) {
	        	logger.error("User not found");
	            throw new UserNotFoundException("User not found: " + username);
	        }
	        return user;
	    }

	    public boolean isAdmin(String username) {
	    	 logger.info("Checking admin list", username);
	        User user = getUserByUsername(username);
	        return "ADMIN".equalsIgnoreCase(user.getRole());
	    }
}