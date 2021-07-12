package it.uniroma3.siw.covidLazio.service;

import javax.transaction.Transactional;

import it.uniroma3.siw.covidLazio.model.*;
import it.uniroma3.siw.covidLazio.repository.FarmaciaRepository;
import it.uniroma3.siw.covidLazio.repository.ProdottoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.covidLazio.repository.UtenteRepository;

import java.util.List;


@Service
public class UtenteService {
	
	@Autowired 
	private UtenteRepository utenteRepository;

	@Autowired
	private ProdottoRepository prodottoRepository;

	@Autowired
	private FarmaciaRepository farmaciaRepository;

	
	@Transactional
	public Utente utentePerId(Long id) {
		return this.utenteRepository.findById(id).orElse(null);
	}

	@Transactional
	public Utente utentePerEmail(String email) { return this.utenteRepository.findByEmail(email).orElse(null); }
	
	@Transactional
	public Utente aggiornaUtente(Utente utente) {
		return this.utenteRepository.save(utente);
	}

	@Transactional
	public List<Prodotto> tuttiIProdotti() { return (List<Prodotto>) this.prodottoRepository.findAll(); }

	@Transactional
	public List<Prodotto> prodottiPerNome(String nome) { return this.prodottoRepository.findByNomeContaining(nome); }

	@Transactional
	public List<Farmacia> farmacieConTamponiDisponibili(Long disponibili) { return this.farmaciaRepository.findByTamponiDisponibiliGreaterThan(disponibili); }

	@Transactional
	public Tampone salvaTamponePrenotato(Utente utente,Farmacia farmacia,Tampone tampone) {
		tampone.setPrezzoVendita(farmacia.getPrezzoTampone());
		tampone.setUtente(utente);
		tampone.setFarmacia(farmacia);
		farmacia.getTamponiPrenotati().add(tampone);
		farmacia.setTamponiDisponibili(farmacia.getTamponiDisponibili() - 1);
		this.farmaciaRepository.save(farmacia);
		return tampone;
	}
	
	
	

}
