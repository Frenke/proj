package com.marco.unicorsi.controllers;

import com.marco.unicorsi.config.KeyProperties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @Autowired
    private KeyProperties key;

    @GetMapping(value = "/login")
    public String getLogin(Model model) {    
        model.addAttribute("publicKey", key.getKey());
        return "login";
    }

}