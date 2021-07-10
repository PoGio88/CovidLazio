package it.uniroma3.siw.covidLazio.service;

import it.uniroma3.siw.covidLazio.model.Locale;
import it.uniroma3.siw.covidLazio.repository.LocaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DipendenteService {

    @Autowired
    private LocaleRepository localeRepository;

    @Transactional
    public Locale localePerId(Long id) {
        return localeRepository.findById(id).orElse(null);
    }

    @Transactional
    public Locale aggiornaLocale(Locale locale) { return localeRepository.save(locale); }
}
