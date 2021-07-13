package it.uniroma3.siw.covidLazio.controller;

import it.uniroma3.siw.covidLazio.model.*;
import it.uniroma3.siw.covidLazio.service.DipendenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class DipendenteController {

    @Autowired
    private DipendenteService dipendenteService;


    @RequestMapping(value = "/dipendente/{id}",method = RequestMethod.GET)
    public String gestisciNegozio(@PathVariable("id") Long id, Model model) {
        Locale locale = dipendenteService.localePerId(id);
        model.addAttribute("locale",locale);
        model.addAttribute("prodotti", locale.getProdotti());
        model.addAttribute("isFarmacia", locale.getDtype().equalsIgnoreCase("Farmacia"));
        //System.out.println(dipendenteService.prodottiPerLocale(locale).size());
        //System.out.println(dipendenteService.prodottiPerLocale(locale).isEmpty());
        return "dipendente/gestioneNegozio.html";
    }

    @RequestMapping(value = "/dipendente/{id}/aggiungiProdotto",method = RequestMethod.GET)
    public String aggiungiProdotto(@PathVariable("id") Long id, Model model) {
        Locale locale = dipendenteService.localePerId(id);
        model.addAttribute("locale",locale);
        model.addAttribute("prodotto",new Prodotto());
        model.addAttribute("isFarmacia", locale.getDtype().equalsIgnoreCase("Farmacia"));
        return "dipendente/aggiungiProdotto.html";
    }

    @RequestMapping(value = "/dipendente/{id}/aggiungiProdotto",method = RequestMethod.POST)
    public String aggiungiProdotto(@PathVariable("id") Long id,@ModelAttribute("prodotto") Prodotto prodotto, Model model) {
        Locale locale = dipendenteService.localePerId(id);
        locale.getProdotti().add(prodotto);
        prodotto.setLocale(locale);
        dipendenteService.aggiornaLocale(locale);
        model.addAttribute("locale", locale);
        model.addAttribute("prodotti",locale.getProdotti());
        model.addAttribute("isFarmacia", locale.getDtype().equalsIgnoreCase("Farmacia"));
        return "dipendente/gestioneNegozio.html";
    }

    @RequestMapping(value = "/dipendente/{id}/modificaProdotti",method = RequestMethod.GET)
    public String modificaProdotti(@PathVariable("id") Long id, Model model) {
        Locale locale = dipendenteService.localePerId(id);
        model.addAttribute("prodotti",locale.getProdotti());
        model.addAttribute("locale",locale);
        model.addAttribute("isFarmacia", locale.getDtype().equalsIgnoreCase("Farmacia"));
        return "dipendente/modificaProdotti.html";
    }

    @RequestMapping(value = "/dipendente/{id}/modificaProdotti",method = RequestMethod.POST)
    public String modificaProdotti(@PathVariable("id") Long id, @ModelAttribute(value = "locale") Negozio negozio,  Model model) {
        Locale locale = dipendenteService.localePerId(id);
        List<Prodotto> prodotti = locale.getProdotti();
        for(int i=0;i<prodotti.size();i++) {
            prodotti.get(i).setPrezzo(negozio.getProdotti().get(i).getPrezzo());
            prodotti.get(i).setDisponibile(negozio.getProdotti().get(i).isDisponibile());
        }
        dipendenteService.aggiornaLocale(locale);
        model.addAttribute("prodotti",locale.getProdotti());
        model.addAttribute("locale",locale);
        model.addAttribute("isFarmacia", locale.getDtype().equalsIgnoreCase("Farmacia"));
        return "dipendente/gestioneNegozio.html";
    }

    @RequestMapping(value = "/dipendente/{id}/gestisciTamponi",method = RequestMethod.GET)
    public String gestisciTamponi(@PathVariable("id") Long id, Model model) {
        Farmacia farmacia = (Farmacia) dipendenteService.localePerId(id);
        model.addAttribute("isFarmacia",true);
        model.addAttribute("locale",farmacia);
        return "dipendente/gestisciTamponi.html";
    }

    @RequestMapping(value = "/dipendente/{id}/gestisciTamponi",method = RequestMethod.POST)
    public String gestisciTamponi(@PathVariable("id") Long id,@ModelAttribute(value = "locale") Farmacia locale, Model model) {
        Farmacia farmacia = (Farmacia) dipendenteService.localePerId(id);
        farmacia.setTamponiDisponibili(locale.getTamponiDisponibili());
        farmacia.setPrezzoTampone(locale.getPrezzoTampone());
        dipendenteService.aggiornaLocale(farmacia);
        System.out.println("aggiornato");
        model.addAttribute("locale",farmacia);
        model.addAttribute("isFarmacia",true);
        return "dipendente/gestioneNegozio.html";
    }

    @RequestMapping(value = "/dipendente/{id}/aggiungiEsiti",method = RequestMethod.POST)
    public String aggiungiEsiti(@PathVariable("id") Long id,@ModelAttribute(value = "locale") Farmacia locale, Model model) {
        Farmacia farmacia = (Farmacia) dipendenteService.localePerId(id);
        for(int i=0;i<farmacia.getTamponiPrenotati().size();i++) {
            farmacia.getTamponiPrenotati().get(i).setEsito(locale.getTamponiPrenotati().get(i).getEsito());
        }
        dipendenteService.aggiornaLocale(farmacia);
        model.addAttribute("locale",farmacia);
        model.addAttribute("isFarmacia",true);
        return "dipendente/gestioneNegozio.html";
    }



}
