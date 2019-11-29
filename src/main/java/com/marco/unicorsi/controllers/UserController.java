package com.marco.unicorsi.controllers;

import java.security.Principal;

import com.marco.unicorsi.model.Comunicazione;
import com.marco.unicorsi.model.Corso;
import com.marco.unicorsi.model.Lezione;
import com.marco.unicorsi.model.Professore;
import com.marco.unicorsi.model.User;
import com.marco.unicorsi.repository.ComunicationRepo;
import com.marco.unicorsi.repository.CorsoRepo;
import com.marco.unicorsi.repository.LessonRepo;
import com.marco.unicorsi.repository.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(path = "/user")
@PreAuthorize("hasAuthority('USER')")
public class UserController{

    @Autowired
    UserRepo userRepo;

    @Autowired
    CorsoRepo corsoRepo;

    @Autowired
    LessonRepo lessonRepo;

    @Autowired
    ComunicationRepo comRepo;

    @GetMapping(value={"/index","/"} )
    public ModelAndView getUsers(Principal principal) {
        //L'oggetto va instanziato nei metodi, infatti di ogni classe vi è una singola istanza
        //mentre ad ogni richiesta si crea un thread, se fosse globale per la classe ci
        //sarebbero problemi di concorrenza 
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
    }//TODO rivedere metodo

    @GetMapping(value="/user-home")
    public String getUserHome(Principal principal, Model model) {
        Professore pr = userRepo.findByUsername(principal.getName()).getDocente();
        if(pr!=null) {
            model.addAttribute("docente", pr);
        } else {
            return "redirect: /admin/admin-home";
        }
        return "/user/user-home";
    }

    @GetMapping(value="/update-corso")
    public ModelAndView getUpdateCorso(@RequestParam String codice, @RequestParam String anno, Principal principal) {
        ModelAndView mView = new ModelAndView();
        //La modifica del corso è consentita solo all'utente titolare del corso
        Corso corso = corsoRepo.findByInsegnamentoAndAnnoAccademico(codice, anno);
        //User user = userRepo.findByUsername(principal.getName());
        if(isOwner(principal, corso)){
            mView.setViewName("/user/update-corso");
            mView.addObject("newLez", new Lezione(corso));
            mView.addObject("newCom", new Comunicazione(corso));
            mView.addObject("corso", corso);
        } else {
            mView.setViewName("/error");
        }
        return mView;
    }

    @PostMapping(value = "/add-lezione")
    public String postAddLezione(@ModelAttribute Lezione lezione, RedirectAttributes redirectAttributes, Principal principal){
        Corso corso = lezione.getCorso();
        if(isOwner(principal, corso)){
            lessonRepo.save(lezione);
            redirectAttributes.addAttribute("codice", corso.getInsegnamento().getCodice());
            redirectAttributes.addAttribute("anno", corso.getAnnoAccademico());
            return "redirect:/user/update-corso";
        } else {
            return "/error";
        }
    }

    @PostMapping(value = "/delete-lesson")
    public String deleteLezione(@RequestParam String codice, @RequestParam String anno, 
         @RequestParam int idLezione, RedirectAttributes redirectAttributes, Principal principal){
        Corso corso = corsoRepo.findByInsegnamentoAndAnnoAccademico(codice, anno);
        if(isOwner(principal, corso)){
            redirectAttributes.addAttribute("codice", codice);
            redirectAttributes.addAttribute("anno", anno);
            lessonRepo.deleteById(idLezione);
            return "redirect:/user/update-corso";
        } else {
            return "/error";
        }
    }

    @PostMapping(value = "/update-prog")
    public String updateProg(@RequestParam String programma, @RequestParam int idCorso,
            RedirectAttributes rAttributes, Principal principal){
        try {
            //L'oggetto corso non viene passato nella chiamata, occorre ritrovarlo nel db per salvarlo
            Corso corso = corsoRepo.findById(idCorso).get();
            corso.setProgramma(programma);
            if(!isOwner(principal, corso))
                return "/error";
            rAttributes.addAttribute("codice", corso.getInsegnamento().getCodice());
            rAttributes.addAttribute("anno", corso.getAnnoAccademico());
            corsoRepo.save(corso);
        } catch(Exception e) {
            return "/error";
        }
        return "redirect:/user/update-corso";
    }

    @PostMapping(value = "/add-com")
    public String postAddLezione(@ModelAttribute Comunicazione comunicazione, RedirectAttributes redirectAttributes, Principal principal){
        Corso corso = comunicazione.getCorso();
        if(isOwner(principal, corso)){
            comRepo.save(comunicazione);
            redirectAttributes.addAttribute("codice", corso.getInsegnamento().getCodice());
            redirectAttributes.addAttribute("anno", corso.getCorso().getAnnoAccademico());
            return "redirect:/user/update-corso";
        } else {
            return "/error";
        }
    }

    //Controlla se chi sta eseguendo la richiesta è titolare del corso
    private boolean isOwner(Principal principal, Corso corso){
        User user = userRepo.findByUsername(principal.getName());
        if(corso.getTitolari().contains(user.getDocente()))
            return true;
        return false;
    }
   
    

}