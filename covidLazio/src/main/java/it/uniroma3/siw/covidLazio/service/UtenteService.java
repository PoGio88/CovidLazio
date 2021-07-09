package it.uniroma3.siw.covidLazio.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.covidLazio.model.Utente;
import it.uniroma3.siw.covidLazio.model.Vaccino;
import it.uniroma3.siw.covidLazio.repository.UtenteRepository;
import it.uniroma3.siw.covidLazio.repository.VaccinoRepository;

@Service
public class UtenteService {
	
	@Autowired 
	private UtenteRepository utenteRepository;
	
	@Autowired 
	private VaccinoRepository vaccinoRepository;
	
	@Transactional
	public Vaccino inserisciVaccino(Vaccino vaccino) {
		return vaccinoRepository.save(vaccino);
	}
	
	@Transactional
	public Utente utentePerId(Long id) {
		return this.utenteRepository.findById(id).orElse(null);
	}
	
	@Transactional
	public Utente aggiornaUtente(Utente utente) {
		return this.utenteRepository.save(utente);
	}
	
	
	
	

}
