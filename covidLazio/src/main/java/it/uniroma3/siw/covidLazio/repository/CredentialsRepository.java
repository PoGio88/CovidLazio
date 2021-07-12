package it.uniroma3.siw.covidLazio.repository;

import java.util.Optional;

import it.uniroma3.siw.covidLazio.model.Utente;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.covidLazio.model.Credentials;


public interface CredentialsRepository extends CrudRepository<Credentials, Long> {
	
	public Optional<Credentials> findByUsername(String username);

	public Optional<Credentials> findByUtente(Utente utente);
}
