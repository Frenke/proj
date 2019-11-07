package com.marco.unicorsi.controllers;


import com.marco.unicorsi.repository.ProfRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class GuestController{

    @Autowired
    ProfRepo profRepo;

    @RequestMapping("/docenti")
    public ModelAndView getDocenti(){
        ModelAndView mViewDocenti = new ModelAndView("docenti");
        mViewDocenti.addObject("docenti", profRepo.findAll());
        return mViewDocenti;
    }

}