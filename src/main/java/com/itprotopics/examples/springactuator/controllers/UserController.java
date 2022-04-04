package com.itprotopics.examples.springactuator.controllers;

import java.util.List;
import java.util.NoSuchElementException;

import com.itprotopics.examples.springactuator.entities.User;
import com.itprotopics.examples.springactuator.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/users")
public class UserController {
  
  @Autowired
  private UserRepository userRepository;
  
  @GetMapping
  public List<User> getAllUsers() {
    return userRepository.findAll();
  }
  @GetMapping("/{id}")
  public ResponseEntity<User> getUserById(@PathVariable(value = "id") Long userId){
      
    User user = userRepository.findById(userId)
          .orElseThrow(() -> new NoSuchElementException("User not availbele for Id :"+userId));
        
    return ResponseEntity.ok().body(user);
  }
  
}