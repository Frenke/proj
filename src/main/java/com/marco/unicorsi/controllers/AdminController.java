package com.marco.unicorsi.controllers;

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
    public String getHome(Model model){
        model.addAttribute("opOk", false);
        model.addAttribute("opError", false);
        return "admin/admin-home";
    }

    @GetMapping(value = "/add-user")
    public ModelAndView getAddUser(){
        ModelAndView mViewAddUser = new ModelAndView("admin/add-user");
        mViewAddUser.addObject("user", new User());
        return mViewAddUser;
    }

    //Chiamato quando si invia il form dalla pagina add user
    @PostMapping(value = "/add-user")
    public String addUser(@ModelAttribute User user, Model model){
        if(isAlsoDocente(user))
            profRepo.save(user.getDocente());
        else //Se non è anche docente si setta a null, Spring di default setta l'oggetto non come null ma come vuoto
            user.setDocente(null);
        if(userRepo.findByUsername(user.getUsername()) == null){
            userSrvc.saveUser(user);
            model.addAttribute("opOk", true);       
            model.addAttribute("resMsg", "Utente creato con successo");
        } else {
            model.addAttribute("opError", true);
            model.addAttribute("errMsg", "Errore: username già utilizzato");
        }

        return "admin/admin-home";
    }

    @GetMapping(value = "/add-docente")
    public ModelAndView getAddDocente(){
        ModelAndView mView = new ModelAndView("admin/add-docente");
        mView.addObject("prof", new Professore());
        return mView;
    }

    @PostMapping(value = "/add-docente")
    public String addDocente(@ModelAttribute Professore professore, Model model){
        profRepo.save(professore);
        model.addAttribute("opOk", true);
        model.addAttribute("resMsg", "Docente creato con successo");
        return "admin/admin-home";
    }

    @GetMapping(value = "/add-insegnamento")
    public ModelAndView getAddIns(){
        ModelAndView mView =  new ModelAndView("admin/add-insegnamento");
        mView.addObject("ins", new Insegnamento());
        return mView;
    }

    @PostMapping(value = "/add-insegnamento")
    public String addInsegnamento(@ModelAttribute Insegnamento insegnamento, Model model){
        insRepo.save(insegnamento);
        model.addAttribute("opOk", true);
        model.addAttribute("resMsg", "Insegnamento aggiunto");
        return "admin/admin-home";
    } 

    @GetMapping(value = "/update-user")
    public ModelAndView getUpdateUser(@RequestParam int id){
        mViewGlobal.setViewName("admin/update-user");
        mViewGlobal.addObject("user", userRepo.findById(id));
        return mViewGlobal;
    }

    @PostMapping(value = "/update-user")
    public String updateUser(@ModelAttribute User user, Model model){
        if(isAlsoDocente(user))
            profRepo.save(user.getDocente());
        else
            user.setDocente(null); //Occorre settare il campo a null altrimenti vengono generate query con inserimento di campi vuoti che violano le constraint del db
        userSrvc.updateUser(user);
        model.addAttribute("opOk", true);
        model.addAttribute("resMsg", "Utente modificato");
        return "admin/admin-home";
    }

    @GetMapping(value = "/search-user")
    public ModelAndView searchUser(@RequestParam(required = false) String username){
        ModelAndView mView = new ModelAndView("admin/admin-home");
        mView.addObject("searchRes", userRepo.findByUsernameContains(username));
        return mView;
    }

    @GetMapping(value = "/user-docente")
    public ModelAndView getUserDocente(@RequestParam int idUser){
        ModelAndView mView = new ModelAndView("admin/user-docente");
        mView.addObject("user", userRepo.findById(idUser).get());
        return mView;
    }

    @GetMapping(value = "/search-docente")
    public String getDocenti(Model model, @RequestParam String cognome, @RequestParam int idUser){
        model.addAttribute("searchRes", profRepo.findByCognomeContains(cognome));
        model.addAttribute("idUser", idUser);
        return "/fragments/docenti-search-res :: resList";
    }

    @PostMapping(value = "/assegna-docente")
    public String assegnaDocentePost(@RequestParam int idDoc, @RequestParam int idUser, Model model){
        try{
            Professore doc = profRepo.findById(idDoc).get();
            User user = userRepo.findById(idUser).get();
            user.setDocente(doc);
            userRepo.save(user);
            model.addAttribute("opOk", true);
            model.addAttribute("resMsg", "Assegnato con successo!");
        } catch(Exception e) {
            model.addAttribute("opError", true);
            model.addAttribute("errMsg", "Errore! " + e.getMessage());
        }
        return "admin/admin-home";
    }

    /* Se lo user è anche un docente deve avere i campi nome, cognome e mail non vuoti */
    private boolean isAlsoDocente(User user){
        Professore professore = user.getDocente();
        if(professore.getNome().isEmpty())
            return false;
        else if(professore.getCognome().isEmpty())
            return false;
        else if(professore.getMail().isEmpty())
            return false;
        return true;
    }
}