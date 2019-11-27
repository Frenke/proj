package com.marco.unicorsi.repository;

import java.util.List;

import com.marco.unicorsi.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Integer>{

    public User findByUsername(String username);

    public List<User> findByUsernameContains(String username);

}