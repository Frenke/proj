package com.marco.unicorsi.repository;

import com.marco.unicorsi.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Integer>{

    public User findByUsername(String username);

}