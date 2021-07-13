package it.uniroma3.siw.covidLazio.controller.validator;

import it.uniroma3.siw.covidLazio.model.Utente;
import it.uniroma3.siw.covidLazio.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;


@Component
public class UtenteValidator implements Validator {

    final Integer MAX_NAME_LENGTH = 100;
    final Integer MIN_NAME_LENGTH = 2;

    @Autowired
    private UtenteService utenteService;

    @Override
    public void validate(Object o, Errors errors) {
        Utente utente = (Utente) o;
        String nome = utente.getNome().trim();
        String cognome = utente.getCognome().trim();
        String email = utente.getEmail().trim();
        LocalDate dataNascita = utente.getDataNascita();


         if (nome.length() < MIN_NAME_LENGTH || nome.length() > MAX_NAME_LENGTH)
            errors.rejectValue("nome", "size");


         if (cognome.length() < MIN_NAME_LENGTH || cognome.length() > MAX_NAME_LENGTH)
            errors.rejectValue("cognome", "size");

        if (utenteService.emailGiaPresente(email))
            errors.rejectValue("email","duplicate");

        if(dataNascita.isAfter(LocalDate.now())) {
            errors.rejectValue("dataNascita","invalid");
        }
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Utente.class.equals(clazz);
    }

}