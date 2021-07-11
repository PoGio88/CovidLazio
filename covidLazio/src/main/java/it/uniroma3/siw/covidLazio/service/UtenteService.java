package it.uniroma3.siw.covidLazio.service;

import javax.transaction.Transactional;

import it.uniroma3.siw.covidLazio.model.Prodotto;
import it.uniroma3.siw.covidLazio.repository.ProdottoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.covidLazio.model.Utente;
import it.uniroma3.siw.covidLazio.model.Vaccino;
import it.uniroma3.siw.covidLazio.repository.UtenteRepository;

import java.util.List;


@Service
public class UtenteService {
	
	@Autowired 
	private UtenteRepository utenteRepository;

	@Autowired
	private ProdottoRepository prodottoRepository;


	
	@Transactional
	public Utente utentePerId(Long id) {
		return this.utenteRepository.findById(id).orElse(null);
	}
	
	@Transactional
	public Utente aggiornaUtente(Utente utente) {
		return this.utenteRepository.save(utente);
	}

	@Transactional
	public List<Prodotto> tuttiIProdotti() { return (List<Prodotto>) this.prodottoRepository.findAll(); }

	@Transactional
	public List<Prodotto> prodottiPerNome(String nome) { return this.prodottoRepository.findByNomeContaining(nome); }


	
	
	
	

}
