package com.marco.unicorsi.controllers;

import java.security.Principal;

import com.marco.unicorsi.model.Corso;
import com.marco.unicorsi.model.Lezione;
import com.marco.unicorsi.model.Professore;
import com.marco.unicorsi.model.User;
import com.marco.unicorsi.repository.CorsoRepo;
import com.marco.unicorsi.repository.LessonRepo;
import com.marco.unicorsi.repository.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
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
        ModelAndView mView = new ModelAndView();
        mView.setViewName("/user/user-home");
        Professore pr = userRepo.findByUsername(principal.getName()).getDocente();
        if(pr!=null) {
            mView.addObject("docente", pr);
        }
        return mView;
    }

    @GetMapping(value="/update-corso")
    public ModelAndView getUpdateCorso(@RequestParam String codice, @RequestParam String anno, Principal principal) {
        ModelAndView mView = new ModelAndView();
        //La modifica del corso è consentita solo all'utente titolare del corso
        Corso corso = corsoRepo.findByInsegnamentoAndAnnoAccademico(codice, anno);
        User user = userRepo.findByUsername(principal.getName());
        if(corso.getTitolari().contains(user.getDocente())){
            mView.setViewName("/user/update-corso");
            mView.addObject("newLez", new Lezione(corso));
            mView.addObject("corso", corso);
        } else {
            mView.setViewName("/error");
        }
        return mView;
    }

    @PostMapping(value = "/add-lezione")
    public String postAddLezione(@ModelAttribute Lezione lezione, RedirectAttributes redirectAttributes){
        lessonRepo.save(lezione);
        redirectAttributes.addAttribute("codice", lezione.getCorso().getInsegnamento().getCodice());
        redirectAttributes.addAttribute("anno", lezione.getCorso().getAnnoAccademico());
        return "redirect:/user/update-corso";
    }

    @PostMapping(value = "/delete-lesson")
    public String deleteLezione(@RequestParam String codice, @RequestParam String anno, @RequestParam int idLezione, RedirectAttributes redirectAttributes){
        redirectAttributes.addAttribute("codice", codice);
        redirectAttributes.addAttribute("anno", anno);
        lessonRepo.deleteById(idLezione);
        return "redirect:/user/update-corso";
    }
    

}