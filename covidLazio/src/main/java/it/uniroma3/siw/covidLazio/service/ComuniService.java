package it.uniroma3.siw.covidLazio.service;

import it.uniroma3.siw.covidLazio.helper.ComuniHelper;
import it.uniroma3.siw.covidLazio.model.Localita;
import it.uniroma3.siw.covidLazio.repository.LocalitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ComuniService {

    @Autowired
    private LocalitaRepository localitaRepository;

    @Transactional
    public void caricaComuni() {
        List<Localita> localitaList = null;
        try {
            localitaList = ComuniHelper.getComuniLazio();
        } catch (IOException e) {
            e.printStackTrace();
        }
        localitaRepository.saveAll(localitaList);
    }

    @Transactional
    public List<Localita> tutteLeLocalita() {
        return (List<Localita>) localitaRepository.findAll();
    }
}
