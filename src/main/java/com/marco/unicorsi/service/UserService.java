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

    public void saveAdmin(User user){
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRuoli(new HashSet<Role>(Arrays.asList(roleRepo.getByRole("USER"), roleRepo.getByRole("ADMIN"))));
        userRepo.save(user);
    }

    public void updateUser(User user){
        if(!user.getPassword().isEmpty()){
            user.setPassword(encoder.encode(user.getPassword()));
            System.out.println("Password codificata");
            userRepo.save(user);
        } else {
            /*  Se la password è vuota non cambio quella precedente,
                la query generata da hibernate non permette ciò 
                quindi occorre recuperarla dal db e settarla nell'oggetto da salvare */
            User toUpdate = userRepo.findById(user.getIdUser()).get();
            user.setPassword(toUpdate.getPassword()); 
            userRepo.save(user);
        }
    }


}