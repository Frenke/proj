package com.marco.unicorsi.controllers;

import java.security.Principal;

import com.marco.unicorsi.model.Insegnamento;
import com.marco.unicorsi.model.Professore;
import com.marco.unicorsi.model.User;
import com.marco.unicorsi.repository.InsRepo;
import com.marco.unicorsi.repository.ProfRepo;
import com.marco.unicorsi.repository.UserRepo;
import com.marco.unicorsi.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(path = "/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController{

    @Autowired
    UserService userSrvc;

    @Autowired
    UserRepo userRepo;

    @Autowired
    ProfRepo profRepo;

    @Autowired
    InsRepo insRepo;

    private ModelAndView mViewGlobal = new ModelAndView();

    @GetMapping(value = "/admin-home")
    public ModelAndView getHome(Principal principal){
        ModelAndView mView = new ModelAndView("/admin/admin-home");
        //mView.addObject("isAdmin", false);
        //mView.addObject("user", new User());
        //mView.addObject("prof", new Professore());
        mView.addObject("opOk", false);
        mView.addObject("opError", false);
        return mView;
    }

    @GetMapping(value = "/add-user")
    public ModelAndView getAddUser(){
        ModelAndView mViewAddUser = new ModelAndView("/admin/add-user");
        mViewAddUser.addObject("user", new User());
        return mViewAddUser;
    }

    //Chiamato quando si invia il form dalla pagina add user
    @PostMapping(value = "/add-user")
    public ModelAndView addUser(@ModelAttribute User user){
        userSrvc.saveUser(user);
        ModelAndView mView = new ModelAndView("/admin/admin-home");
        mView.addObject("opOk", true);
        mView.addObject("resMsg", "Utente creato con successo");
        return mView;
    }

    @GetMapping(value = "/add-docente")
    public ModelAndView getAddDocente(){
        ModelAndView mView = new ModelAndView("/admin/add-docente");
        mView.addObject("prof", new Professore());
        return mView;
    }

    @PostMapping(value = "/add-docente")
    public ModelAndView addDocente(@ModelAttribute Professore professore){
        profRepo.save(professore);
        ModelAndView mView = new ModelAndView("/admin/admin-home");
        mView.addObject("opOk", true);
        mView.addObject("resMsg", "Docente creato con successo");
        return mView;
    }

    @GetMapping(value = "/add-insegnamento")
    public ModelAndView getAddIns(){
        ModelAndView mView =  new ModelAndView("/admin/add-insegnamento");
        mView.addObject("ins", new Insegnamento());
        return mView;
    }

    @PostMapping(value = "/add-insegnamento")
    public ModelAndView addInsegnamento(@ModelAttribute Insegnamento insegnamento){
        insRepo.save(insegnamento);
        ModelAndView mView = new ModelAndView("/admin/admin-home");
        mView.addObject("opOk", true);
        mView.addObject("resMsg", "Insegnamento aggiunto");
        return mView;
    } 

    @GetMapping(value = "/update-user")
    public ModelAndView getUpdateUser(@RequestParam int id){
        mViewGlobal.setViewName("/admin/update-user");
        mViewGlobal.addObject("user", userRepo.findById(id));
        return mViewGlobal;
    }

    @PostMapping(value = "/update-user")
    public ModelAndView updateUser(@ModelAttribute User user){
        userSrvc.updateUser(user);
        mViewGlobal.setViewName("/admin/admin-home");
        mViewGlobal.addObject("opOk", true);
        mViewGlobal.addObject("resMsg", "Utente modificato");
        return mViewGlobal;
    }

    @GetMapping(value = "/search-user")
    public ModelAndView searchUser(@RequestParam(required = false) String username){
        ModelAndView mView = new ModelAndView("/admin/admin-home");
        mView.addObject("searchRes", userRepo.findByUsernameContains(username));
        return mView;
    }

    @GetMapping(value = "/user-docente")
    public ModelAndView getUserDocente(@RequestParam int idUser){
        ModelAndView mView = new ModelAndView("/admin/user-docente");
        mView.addObject("user", userRepo.findById(idUser).get());
        return mView;
    }

    @GetMapping(value = "/search-docente")
    public String getDocenti(Model model, @RequestParam String cognome, @RequestParam int idUser){
        //ModelAndView mView = new ModelAndView("/admin/user-docente");
        model.addAttribute("searchRes", profRepo.findByCognomeContains(cognome));
        model.addAttribute("idUser", idUser);
        return "/fragments/docenti-search-res :: resList";
    }

    @PostMapping(value = "/assegna-docente")
    public ModelAndView assegnaDocentePost(@RequestParam int idDoc, @RequestParam int idUser){
        ModelAndView mView = new ModelAndView("/admin/admin-home");
        try{
            Professore doc = profRepo.findById(idDoc).get();
            User user = userRepo.findById(idUser).get();
            user.setDocente(doc);
            userRepo.save(user);
            mView.addObject("opOk", true);
            mView.addObject("resMsg", "Assegnato con successo!");
        } catch(Exception e) {
            mView.addObject("opError", true);
            mView.addObject("errMsg", "Errore! " + e.getMessage());
        }
        return mView;
    }
}