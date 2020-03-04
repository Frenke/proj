package com.marco.unicorsi.controllers;

import java.security.Principal;

import com.marco.unicorsi.model.Avvisi;
import com.marco.unicorsi.model.Comunicazione;
import com.marco.unicorsi.model.Corso;
import com.marco.unicorsi.model.Lezione;
import com.marco.unicorsi.model.Professore;
import com.marco.unicorsi.model.User;
import com.marco.unicorsi.repository.AvvisiRepo;
import com.marco.unicorsi.repository.ComunicationRepo;
import com.marco.unicorsi.repository.CorsoRepo;
import com.marco.unicorsi.repository.LessonRepo;
import com.marco.unicorsi.repository.ProfRepo;
import com.marco.unicorsi.repository.UserRepo;
import com.marco.unicorsi.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

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
    ProfRepo docRepo;

    @Autowired
    UserService userService;

    @Autowired
    ComunicationRepo comRepo;

    @Autowired
    AvvisiRepo avvisiRepo;

    @GetMapping(value="/user-home")
    public String getUserHome(Principal principal, Model model) {
        Professore pr = userRepo.findByUsername(principal.getName()).getDocente();
        if(pr!=null) {
            model.addAttribute("docente", pr);
        } else {
            return "redirect: /admin/admin-home";
        }
        return "user/user-home";
    }

    @GetMapping(value = "/personal-info")
    public String getPersonalInfo(Principal principal, Model model){
        User user = userRepo.findByUsername(principal.getName());
        model.addAttribute("user", user );
        return "user/personal-info";
    }

    @PostMapping(value = "/update-info")
    public String updateInfoPost(Principal principal, @ModelAttribute User user){

        if(principal.getName().equals(user.getUsername())){
            userService.updateUser(user);
            docRepo.save(user.getDocente());
        } else {
            return "error";
        }
        return "redirect:user/user-home";
    }

    @GetMapping(value="/update-corso")
    public String getUpdateCorso(@RequestParam String codice, @RequestParam String anno, Principal principal, Model model) {
        //La modifica del corso è consentita solo all'utente titolare del corso
        Corso corso = corsoRepo.findByInsegnamentoAndAnnoAccademico(codice, anno);
        //User user = userRepo.findByUsername(principal.getName());
        if(isOwner(principal, corso)){
            model.addAttribute("newLez", new Lezione(corso));
            model.addAttribute("newCom", new Comunicazione(corso));
            model.addAttribute("corso", corso);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Non sei il titolare di questo corso");
        }
        return "user/update-corso";
    }

    @PostMapping(value = "/add-lezione")
    public String postAddLezione(@ModelAttribute Lezione lezione, RedirectAttributes redirectAttributes, Principal principal){
        Corso corso = lezione.getCorso();
        if(isOwner(principal, corso)){
            lessonRepo.save(lezione);
            return redirectToUpdateCorso(corso, redirectAttributes);
        } else {
            return "error";
        }
    }

    @PostMapping(value = "/delete-lesson")
    public String deleteLezione(@RequestParam String codice, @RequestParam String anno, 
         @RequestParam int idLezione, RedirectAttributes redirectAttributes, Principal principal){
        Corso corso = corsoRepo.findByInsegnamentoAndAnnoAccademico(codice, anno);
        if(isOwner(principal, corso)){
            lessonRepo.deleteById(idLezione);
            return redirectToUpdateCorso(corso, redirectAttributes);
        } else {
            return "error";
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
                return "error";
            corsoRepo.save(corso);
            return redirectToUpdateCorso(corso, rAttributes);
        } catch(Exception e) {
            return "error";
        }
    }

    @PostMapping(value = "/add-com")
    public String postAddLezione(@ModelAttribute Comunicazione comunicazione, RedirectAttributes redirectAttributes, Principal principal){
        Corso corso = comunicazione.getCorso();
        if(isOwner(principal, corso)){
            comRepo.save(comunicazione);
            return redirectToUpdateCorso(corso, redirectAttributes);
        } else {
            return "error";
        }
    }

    @GetMapping(value = "/del-com")
    public String deleteLezione(@RequestParam int idCom, RedirectAttributes redirectAttributes, Principal principal){
        Corso corso = comRepo.findById(idCom).get().getCorso();
        if(isOwner(principal, corso)){
            comRepo.deleteById(idCom);
            return redirectToUpdateCorso(corso, redirectAttributes);
        } else {
            return "/error";
        }
    }

    @PostMapping(value = "/addAvviso")
    public String addAvviso(Principal principal, String oggetto, String avviso){
        Avvisi newAvviso = new Avvisi();
        newAvviso.setOggetto(oggetto);
        newAvviso.setBody(avviso);
        newAvviso.setAutore(userRepo.findByUsername(principal.getName()));
        avvisiRepo.save(newAvviso);
        return "redirect:/index";
    }

    @GetMapping(value = "/delAvviso")
    public String deleteAvviso(Principal principal, int id){
        User requesting = userRepo.findByUsername(principal.getName());
        Avvisi toDel = avvisiRepo.getOne(id);
        //Solo l'autore del post può cancellarlo
        if(requesting.getIdUser() == toDel.getAutore().getIdUser()){
            avvisiRepo.deleteById(id);
        } 
        return "redirect:/index";
    }

    

    //Controlla se chi sta eseguendo la richiesta è titolare del corso
    private boolean isOwner(Principal principal, Corso corso){
        User user = userRepo.findByUsername(principal.getName());
        if(corso.getTitolari().contains(user.getDocente()))
            return true;
        return false;
    }

    private String redirectToUpdateCorso(Corso corso, RedirectAttributes redirectAttributes){
        redirectAttributes.addAttribute("codice", corso.getInsegnamento().getCodice());
        redirectAttributes.addAttribute("anno", corso.getAnnoAccademico());
        return "redirect:update-corso";
    }

}