package it.uniroma3.siw.covidLazio.service;

import java.util.Optional;

import it.uniroma3.siw.covidLazio.model.Utente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.covidLazio.model.Credentials;
import it.uniroma3.siw.covidLazio.repository.CredentialsRepository;

import static it.uniroma3.siw.covidLazio.model.Credentials.DIPENDENTE_ROLE;


@Service
public class CredentialsService {
	
    @Autowired 
    protected PasswordEncoder passwordEncoder;

	@Autowired
	protected CredentialsRepository credentialsRepository;
	
	@Transactional
	public Credentials getCredentials(Long id) {
		Optional<Credentials> result = this.credentialsRepository.findById(id);
		return result.orElse(null);
	}

	@Transactional
	public Credentials getCredentials(String username) {
		Optional<Credentials> result = this.credentialsRepository.findByUsername(username);
		return result.orElse(null);
	}

	@Transactional
	public Credentials getCredentials(Utente utente) {
		Optional<Credentials> result = this.credentialsRepository.findByUtente(utente);
		return result.orElse(null);
	}
		
    @Transactional
    public Credentials saveCredentials(Credentials credentials) {
        credentials.setRole(Credentials.UTENTE_ROLE);
        credentials.setPassword(this.passwordEncoder.encode(credentials.getPassword()));
        return this.credentialsRepository.save(credentials);
    }

	public void setDipendenteCredentials(Credentials credentials,Long id) {
		String cred = DIPENDENTE_ROLE + "/" + id;
		credentials.setRole(cred);
	}
}
	
