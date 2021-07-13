package it.uniroma3.siw.covidLazio.controller;


import it.uniroma3.siw.covidLazio.helper.RicercaHelper;
import it.uniroma3.siw.covidLazio.model.*;


import it.uniroma3.siw.covidLazio.repository.LocalitaRepository;
import it.uniroma3.siw.covidLazio.service.ComuniService;
import it.uniroma3.siw.covidLazio.service.DipendenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import it.uniroma3.siw.covidLazio.service.CredentialsService;
import it.uniroma3.siw.covidLazio.service.UtenteService;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static it.uniroma3.siw.covidLazio.model.Credentials.DIPENDENTE_ROLE;


@Controller
public class UtenteController {

    @Autowired
    private UtenteService utenteService;

    @Autowired
    private CredentialsService credentialsService;

    @Autowired
    private ComuniService comuniService;

    @Autowired
	private DipendenteService dipendenteService;


    @RequestMapping(value = {"/", "index"}, method = RequestMethod.GET)
    public String index(Model model) {
        if (comuniService.tutteLeLocalita().isEmpty()) {
            comuniService.caricaComuni();
        }
        return "index.html";
    }

    @RequestMapping(value = "/aggiungiVaccino", method = RequestMethod.GET)
    public String aggiungiVaccino(Model model) {
        Utente utenteCorrente = getUtenteCorrente();
        if (utenteCorrente.getVaccino() == null) {
            model.addAttribute("vaccino", new Vaccino());
        } else model.addAttribute("vaccinoInserito", utenteCorrente.getVaccino());
        model.addAttribute("utente", utenteCorrente);
        return "utente/vaccinoForm.html";
    }

    @RequestMapping(value = {"/aggiungiVaccino"}, method = RequestMethod.POST)
    public String aggiungiVaccino(@ModelAttribute("vaccino") Vaccino vaccino, Model model) {
        Utente utenteCorrente = getUtenteCorrente();
        utenteCorrente.setVaccino(vaccino);
        this.utenteService.aggiornaUtente(utenteCorrente);
        model.addAttribute("utente", utenteCorrente);
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
        model.addAttribute("utente", getUtenteCorrente());
        model.addAttribute("locale", new Negozio());
        model.addAttribute("localita", comuniService.tutteLeLocalita());
        return "utente/negozioForm.html";
    }

    @RequestMapping(value = "/aggiungiNegozio", method = RequestMethod.POST)
    public String aggiungiNegozio(@ModelAttribute("locale") Negozio negozio, Model model) {
        Utente utenteCorrente = getUtenteCorrente();
        utenteCorrente.setLocale(negozio);
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
        this.utenteService.aggiornaUtente(utenteCorrente);
        credentialsService.setDipendenteCredentials(credentials, negozio.getId());
        this.utenteService.aggiornaUtente(utenteCorrente);
        this.update(userDetails);
        model.addAttribute("utente", utenteCorrente);
        return "home.html";
    }

    @RequestMapping(value = "/aggiungiFarmacia", method = RequestMethod.GET)
    public String aggiungiFarmacia(Model model) {
        model.addAttribute("utente", getUtenteCorrente());
        model.addAttribute("locale", new Farmacia());
        model.addAttribute("localita", comuniService.tutteLeLocalita());
        return "utente/farmaciaForm.html";
    }

    @RequestMapping(value = "/aggiungiFarmacia", method = RequestMethod.POST)
    public String aggiungiFarmacia(@ModelAttribute("locale") Farmacia farmacia, Model model) {
        Utente utenteCorrente = getUtenteCorrente();
        utenteCorrente.setLocale(farmacia);
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
        this.utenteService.aggiornaUtente(utenteCorrente);
        credentialsService.setDipendenteCredentials(credentials, farmacia.getId());
        this.utenteService.aggiornaUtente(utenteCorrente);
        this.update(userDetails);
        model.addAttribute("utente", utenteCorrente);
        return "home.html";
    }

    @RequestMapping(value = "/visualizzaProdotti", method = RequestMethod.GET)
    public String visualizzaProdotti(Model model) {
        model.addAttribute("utente", getUtenteCorrente());
        model.addAttribute("prodotti", utenteService.tuttiIProdotti());
        model.addAttribute("helper", new RicercaHelper());
        return "utente/visualizzaProdotti.html";
    }

    @RequestMapping(value = "/visualizzaProdotti", method = RequestMethod.POST)
    public String visualizzaProdotti(@ModelAttribute("helper") RicercaHelper helper, Model model) {
        model.addAttribute("prodotti", utenteService.prodottiPerNome(helper.getNome()));
        model.addAttribute("utente", getUtenteCorrente());
        System.out.println("STAMPA IL NOME" + helper.getNome());
        model.addAttribute("helper", new RicercaHelper());
        return "utente/visualizzaProdotti.html";
    }

    @RequestMapping(value = "/tamponiDisponibili", method = RequestMethod.GET)
    public String tamponiDisponibili(Model model) {
        model.addAttribute("utente", getUtenteCorrente());
        model.addAttribute("farmacie", utenteService.farmacieConTamponiDisponibili(0L));
        return "utente/tamponiDisponibili.html";
    }

    @RequestMapping(value = "/prenotaTampone/{id}",method = RequestMethod.GET)
    public String prenotaTampone(@PathVariable("id") Long id, Model model) {
		model.addAttribute("utente", getUtenteCorrente());
		model.addAttribute("farmacia",(Farmacia) dipendenteService.localePerId(id));
		model.addAttribute("tampone",new Tampone());
		return "utente/prenotaTampone.html";
	}

    @RequestMapping(value = "/prenotaTampone/{id}",method = RequestMethod.POST)
    public String prenotaTampone(@PathVariable("id") Long id, @ModelAttribute("tampone") Tampone tampone,Model model) {
        Utente utenteCorrente = this.getUtenteCorrente();
        Farmacia farmacia = (Farmacia) dipendenteService.localePerId(id);
        utenteService.salvaTamponePrenotato(utenteCorrente,farmacia,tampone);
        Utente utente = this.getUtenteCorrente();
        utente.setTampone(tampone);
        model.addAttribute("utente",utente);
        return "home.html";
    }

    @RequestMapping(value = "/visualizzaTampone",method = RequestMethod.GET)
    public String visualizzaTampone(Model model) {
        model.addAttribute("utente",this.getUtenteCorrente());
        return "utente/visualizzaTampone.html";
    }

    public Utente getUtenteCorrente() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
        return this.utenteService.utentePerId(credentials.getUtente().getId());
    }

    private void update(UserDetails userDetails) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userRole = credentialsService.getCredentials(userDetails.getUsername()).getRole();
        List<GrantedAuthority> actualAuthorities = new ArrayList<>();
        actualAuthorities.add(new SimpleGrantedAuthority(userRole));
        Authentication newAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(), actualAuthorities);
        SecurityContextHolder.getContext().setAuthentication(newAuth);
    }
}