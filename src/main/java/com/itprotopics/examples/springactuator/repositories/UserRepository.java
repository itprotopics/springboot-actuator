package com.itprotopics.examples.springactuator.repositories;

import com.itprotopics.examples.springactuator.entities.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
}

