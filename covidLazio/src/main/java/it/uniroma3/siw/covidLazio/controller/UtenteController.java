package it.uniroma3.siw.covidLazio.controller;


import it.uniroma3.siw.covidLazio.helper.RicercaHelper;
import it.uniroma3.siw.covidLazio.model.Credentials;
import it.uniroma3.siw.covidLazio.model.Locale;
import it.uniroma3.siw.covidLazio.model.Negozio;
import it.uniroma3.siw.covidLazio.model.Utente;
import it.uniroma3.siw.covidLazio.model.Vaccino;


import it.uniroma3.siw.covidLazio.repository.LocalitaRepository;
import it.uniroma3.siw.covidLazio.service.ComuniService;
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
import static it.uniroma3.siw.covidLazio.model.Credentials.DIPENDENTE_ROLE;




@Controller
public class UtenteController {
	
	@Autowired 
	private UtenteService utenteService;
	
	@Autowired 
	private CredentialsService credentialsService;

	@Autowired
	private ComuniService comuniService;

    

    @RequestMapping(value = {"/", "index"}, method = RequestMethod.GET)
    public String index(Model model) {
    	//comuniService.caricaComuni();
        return "index.html";
    }

    @RequestMapping(value = "/aggiungiVaccino", method = RequestMethod.GET)
    public String aggiungiVaccino(Model model) {
		Utente utenteCorrente = getUtenteCorrente();
    	if(utenteCorrente.getVaccino() == null) {
        model.addAttribute("vaccino",new Vaccino());
    	} else model.addAttribute("vaccinoInserito", utenteCorrente.getVaccino());
    	model.addAttribute("utente",utenteCorrente);
        return "utente/vaccinoForm.html";
    }
    
    @RequestMapping(value = {"/aggiungiVaccino"}, method = RequestMethod.POST)
    public String aggiungiVaccino(@ModelAttribute("vaccino") Vaccino vaccino, Model model) {
		Utente utenteCorrente = getUtenteCorrente();
    	utenteCorrente.setVaccino(vaccino);
    	this.utenteService.aggiornaUtente(utenteCorrente);
		model.addAttribute("utente",utenteCorrente);
    	return "home.html";
    }

    @RequestMapping(value = "/aggiornaVaccino", method = RequestMethod.GET)
    public String aggiornaVaccino(Model model) {
		Utente utenteCorrente = getUtenteCorrente();
		model.addAttribute(utenteCorrente);
        model.addAttribute("vaccinoDaAggiornare", utenteCorrente.getVaccino());
        return "utente/vaccinoForm.html";
    }

	@RequestMapping(value = "/aggiornaVaccino", method = RequestMethod.POST)
	public String aggiornaVaccino(@ModelAttribute("vaccinoDaAggiornare") Vaccino vaccino, Model model) {
    	Utente utenteCorrente = getUtenteCorrente();
		Vaccino vaccinoAggiornato = utenteCorrente.getVaccino();
		vaccinoAggiornato.setTipologia(vaccino.getTipologia());
		vaccinoAggiornato.setDataPrimaDose(vaccino.getDataPrimaDose());
		vaccinoAggiornato.setDataSecondaDose(vaccino.getDataSecondaDose());
		utenteCorrente.setVaccino(vaccinoAggiornato);
		this.utenteService.aggiornaUtente(utenteCorrente);
		model.addAttribute(utenteCorrente);
		return "home.html";
	}
	
	@RequestMapping(value = "/aggiungiNegozio", method = RequestMethod.GET)
    public String aggiungiNegozio(Model model) {
    	model.addAttribute("utente",getUtenteCorrente());
        model.addAttribute("locale",new Negozio());
        model.addAttribute("localita", comuniService.tutteLeLocalita());
        return "utente/negozioForm.html";
    }
    
    @RequestMapping(value = "/aggiungiNegozio", method = RequestMethod.POST)
    public String aggiungiNegozio(@ModelAttribute("locale") Negozio negozio, Model model) {
		Utente utenteCorrente = getUtenteCorrente();
    	utenteCorrente.setLocale(negozio);
    	UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
		this.utenteService.aggiornaUtente(utenteCorrente);
		credentialsService.setDipendenteCredentials(credentials, negozio.getId());
		this.utenteService.aggiornaUtente(utenteCorrente);
		model.addAttribute(utenteCorrente);
    	return "home.html";
    }

    @RequestMapping(value = "/visualizzaProdotti", method = RequestMethod.GET)
	public String visualizzaProdotti(Model model) {
		model.addAttribute("utente",getUtenteCorrente());
    	model.addAttribute("prodotti",utenteService.tuttiIProdotti());
    	model.addAttribute("helper",new RicercaHelper());
    	return "utente/visualizzaProdotti.html";
	}

	@RequestMapping(value = "/visualizzaProdotti", method = RequestMethod.POST)
	public String visualizzaProdotti(@ModelAttribute("helper") RicercaHelper helper,Model model) {
    	model.addAttribute("prodotti",utenteService.prodottiPerNome(helper.getNome()));
    	model.addAttribute("utente",getUtenteCorrente());
    	System.out.println("STAMPA IL NOME" + helper.getNome());
		model.addAttribute("helper",new RicercaHelper());
		return "utente/visualizzaProdotti.html";
	}

	private Utente getUtenteCorrente() {
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
		return  this.utenteService.utentePerId(credentials.getUtente().getId());
	}
    

}