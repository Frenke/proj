package com.marco.unicorsi.controllers;

import java.security.Principal;

import com.marco.unicorsi.model.Professore;
import com.marco.unicorsi.repository.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;



@Controller
@RequestMapping(path = "/user")
@PreAuthorize("hasAuthority('USER')")
public class UserController{

    @Autowired
    UserRepo userRepo;

    ModelAndView mView = new ModelAndView();

    @GetMapping(value={"/index","/"} )
    public ModelAndView getUsers(Principal principal) {

        ModelAndView model = new ModelAndView();

        if(principal != null){
            System.out.println("AUTHENR");
            model.addObject("username", principal.getName());
            model.addObject("isLogged", true);
            model.setViewName("index");
            return model;
        }
        model.addObject("isLogged", false);
        model.setViewName("index");
        return model;
    }

    @GetMapping(value="/user-home")
    public ModelAndView getMethodName(Principal principal) {
        mView.setViewName("/user/user-home");
        Professore pr = userRepo.findByUsername(principal.getName()).getDocente();
        if(pr!=null)
            mView.addObject("docente", pr);

        return mView;
    }
    
    

}