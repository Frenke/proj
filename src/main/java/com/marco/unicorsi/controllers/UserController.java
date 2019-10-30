package com.marco.unicorsi.controllers;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class UserController{

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
    

}