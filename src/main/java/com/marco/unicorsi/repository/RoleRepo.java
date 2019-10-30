package com.marco.unicorsi.repository;

import com.marco.unicorsi.model.Role;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Integer>{
    
}