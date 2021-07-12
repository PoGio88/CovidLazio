package it.uniroma3.siw.covidLazio.service;

import it.uniroma3.siw.covidLazio.model.Farmacia;
import it.uniroma3.siw.covidLazio.model.Locale;
import it.uniroma3.siw.covidLazio.model.Negozio;
import it.uniroma3.siw.covidLazio.model.Prodotto;
import it.uniroma3.siw.covidLazio.repository.FarmaciaRepository;
import it.uniroma3.siw.covidLazio.repository.LocaleRepository;
import it.uniroma3.siw.covidLazio.repository.ProdottoRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class DipendenteService {

    @Autowired
    private LocaleRepository localeRepository;
    
    @Autowired 
    private ProdottoRepository prodottoRepositoy;

    @Autowired
    private FarmaciaRepository farmaciaRepository;

    @Transactional
    public Locale localePerId(Long id) {
        return localeRepository.findById(id).orElse(null);
    }

    @Transactional
    public Locale aggiornaLocale(Locale locale) { return localeRepository.save(locale); }
    
    @Transactional
    public List<Prodotto> prodottiPerLocale(Locale locale){
    	return this.prodottoRepositoy.findByLocale(locale);
    }

}
