package com.marco.unicorsi.controllers;
import java.util.List;

import com.marco.unicorsi.model.Corso;
import com.marco.unicorsi.repository.AvvisiRepo;
import com.marco.unicorsi.repository.CorsoRepo;
import com.marco.unicorsi.repository.InsRepo;
import com.marco.unicorsi.repository.NewsRepo;
import com.marco.unicorsi.repository.ProfRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class GuestController {

    @Autowired
    ProfRepo profRepo;

    @Autowired
    InsRepo insRepo;

    @Autowired
    CorsoRepo corsoRepo;

    @Autowired
    AvvisiRepo avvisiRepo;

    @Autowired
    NewsRepo newsRepo;

    @RequestMapping({"/index","/"})
    public ModelAndView getIndex() {
        ModelAndView mvView = new ModelAndView("index");
        mvView.addObject("avvisi", avvisiRepo.findAll(Sort.by(Sort.Direction.DESC, "data")));
        mvView.addObject("news", newsRepo.findAll(Sort.by(Sort.Direction.DESC, "data")));
        return mvView;
    }

    @RequestMapping("/docenti")
    public ModelAndView getDocenti() {
        ModelAndView mViewDocenti = new ModelAndView("docenti");
        mViewDocenti.addObject("docenti", profRepo.findAll());
        return mViewDocenti;
    }

    @RequestMapping("/insegnamenti")
    public ModelAndView getInsegnamenti(@RequestParam String anno) {
        ModelAndView mViewIns = new ModelAndView("insegnamenti");
        List<Corso> corsi = corsoRepo.findByAnnoAccademico(anno);
        mViewIns.addObject("corsi", corsi);
        mViewIns.addObject("annoAcc", anno);
        return mViewIns;
    }

    @RequestMapping("/corso")
    public ModelAndView getCorso(@RequestParam String codice, @RequestParam String anno) {
        ModelAndView mViewCorso = new ModelAndView("corso");
        mViewCorso.addObject("corso", corsoRepo.findByInsegnamentoAndAnnoAccademico(codice, anno));
        return mViewCorso;
    }



}