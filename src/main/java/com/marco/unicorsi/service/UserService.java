package com.marco.unicorsi.service;

import java.util.Arrays;
import java.util.HashSet;

import com.marco.unicorsi.model.Role;
import com.marco.unicorsi.model.User;
import com.marco.unicorsi.repository.RoleRepo;
import com.marco.unicorsi.repository.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService{

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private BCryptPasswordEncoder encoder;

    public void saveUser(User user){
        user.setPassword(encoder.encode(user.getPassword()));
        Role ruolo = roleRepo.getByRole("USER");
        user.setRuoli(new HashSet<Role>(Arrays.asList(ruolo)));
        userRepo.save(user);
    }

    
}