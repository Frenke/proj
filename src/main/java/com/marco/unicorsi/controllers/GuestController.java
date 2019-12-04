package com.marco.unicorsi.controllers;


import com.marco.unicorsi.repository.CorsoRepo;
import com.marco.unicorsi.repository.InsRepo;
import com.marco.unicorsi.repository.ProfRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class GuestController{

    @Autowired
    ProfRepo profRepo;

    @Autowired
    InsRepo insRepo;

    @Autowired
    CorsoRepo corsoRepo;

    @RequestMapping("/index")
    public ModelAndView getIndex(){
        ModelAndView mvView = new ModelAndView("index");
        return mvView;
    }

    @RequestMapping("/docenti")
    public ModelAndView getDocenti(){
        ModelAndView mViewDocenti = new ModelAndView("docenti");
        mViewDocenti.addObject("docenti", profRepo.findAll());
        return mViewDocenti;
    }

    @RequestMapping("/insegnamenti")
    public ModelAndView getInsegnamenti(@RequestParam String anno){
        ModelAndView mViewIns = new ModelAndView("insegnamenti");
        mViewIns.addObject("insegnamenti", insRepo.getInsByAnno(anno));
        mViewIns.addObject("annoAcc", anno);
        return mViewIns;
    }

    @RequestMapping("/corso")
    public ModelAndView getCorso(@RequestParam String codice, @RequestParam String anno){
        ModelAndView mViewCorso = new ModelAndView("/corso");
        mViewCorso.addObject("corso", corsoRepo.findByInsegnamentoAndAnnoAccademico(codice, anno));
        return mViewCorso;
    }

}