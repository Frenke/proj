package com.marco.unicorsi.controllers;

import java.security.Principal;

import com.marco.unicorsi.model.Professore;
import com.marco.unicorsi.model.User;
import com.marco.unicorsi.repository.ProfRepo;
import com.marco.unicorsi.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(path = "/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController{

    @Autowired
    UserService userSrvc;

    @Autowired
    ProfRepo profRepo;

    @GetMapping(value = "/admin-home")
    public ModelAndView getHome(Principal principal){
        ModelAndView mView = new ModelAndView("/admin/admin-home");
        mView.addObject("user", new User());
        mView.addObject("prof", new Professore());
        return mView;
    }

    @PostMapping(value = "/add-user")
    public String addUser(@ModelAttribute User user){
        userSrvc.saveUser(user);
        return "admin/admin-home";
    }

    @PostMapping(value = "/add-professor")
    public String addProfessor(@ModelAttribute Professore professore){
        profRepo.save(professore);
        return "admin/admin-home";
    }
}