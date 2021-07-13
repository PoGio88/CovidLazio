package it.uniroma3.siw.covidLazio.repository;

import it.uniroma3.siw.covidLazio.model.Utente;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface UtenteRepository extends CrudRepository<Utente,Long> {
	
	public Optional<Utente> findById(Long id);

	public Optional<Utente> findByEmail(String email);

}
