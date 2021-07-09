package it.uniroma3.siw.covidLazio.controller;


import it.uniroma3.siw.covidLazio.model.Vaccino;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.covidLazio.service.CredentialsService;




@Controller
public class UtenteController {

    

    @RequestMapping(value = {"/", "index"}, method = RequestMethod.GET)
    public String index(Model model) {
        return "index.html";
    }

    @GetMapping(value = "/aggiungiVaccino")
    public String aggiungiVaccino(Model model) {
        model.addAttribute("vaccino",new Vaccino());
        return "vaccinoForm";
    }

}