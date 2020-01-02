package com.marco.unicorsi.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping(value = "/login")
    public String getLogin(Model model) {
        
        model.addAttribute("publicKey", "1234567812345678");
        return "login";
    }

}