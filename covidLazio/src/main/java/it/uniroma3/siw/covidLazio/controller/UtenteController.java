package it.uniroma3.siw.covidLazio.controller;


import it.uniroma3.siw.covidLazio.model.Credentials;
import it.uniroma3.siw.covidLazio.model.Utente;
import it.uniroma3.siw.covidLazio.model.Vaccino;
import it.uniroma3.siw.covidLazio.repository.VaccinoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.covidLazio.service.CredentialsService;
import it.uniroma3.siw.covidLazio.service.UtenteService;




@Controller
public class UtenteController {
	
	@Autowired 
	private UtenteService utenteService;
	
	@Autowired 
	private CredentialsService credentialsService;

    

    @RequestMapping(value = {"/", "index"}, method = RequestMethod.GET)
    public String index(Model model) {
        return "index.html";
    }

    @RequestMapping(value = "/aggiungiVaccino", method = RequestMethod.GET)
    public String aggiungiVaccino(Model model) {
    	UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
    	Utente utenteCorrente = this.utenteService.utentePerId(credentials.getUtente().getId());
    	if(utenteCorrente.getVaccino() == null) {
        model.addAttribute("vaccino",new Vaccino());
    	} else model.addAttribute("vaccinoInserito", utenteCorrente.getVaccino());
        return "utente/vaccinoForm.html";
    }
    
    @RequestMapping(value = "/aggiungiVaccino", method = RequestMethod.POST)
    public String aggiungiVaccino(@ModelAttribute("vaccino") Vaccino vaccino, Model model) {
    	UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
    	Utente utenteCorrente = this.utenteService.utentePerId(credentials.getUtente().getId());
    	Vaccino vaccinoFinale = utenteCorrente.getVaccino();
    	if(vaccinoFinale != null) {
    	vaccinoFinale.setTipologia(vaccino.getTipologia());
    	vaccinoFinale.setDataPrimaDose(vaccino.getDataPrimaDose());
    	vaccinoFinale.setDataSecondaDose(vaccino.getDataSecondaDose());
    	utenteCorrente.setVaccino(vaccinoFinale);
    	} else {
    	utenteService.inserisciVaccino(vaccino);
    	utenteCorrente.setVaccino(vaccino);
    	}
    	this.utenteService.aggiornaUtente(utenteCorrente);
    	return "index.html";
        
    }
    @RequestMapping(value = "/aggiornaVaccino", method = RequestMethod.GET)
    public String aggiornaVaccino(Model model) {
    	UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
    	Utente utenteCorrente = this.utenteService.utentePerId(credentials.getUtente().getId());
        model.addAttribute("vaccino", utenteCorrente.getVaccino());
        return "utente/vaccinoForm.html";
    }
    

}