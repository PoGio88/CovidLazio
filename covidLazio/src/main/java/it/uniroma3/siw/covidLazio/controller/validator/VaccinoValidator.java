package it.uniroma3.siw.covidLazio.controller.validator;

import it.uniroma3.siw.covidLazio.model.Utente;
import it.uniroma3.siw.covidLazio.model.Vaccino;
import it.uniroma3.siw.covidLazio.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;

@Component
public class VaccinoValidator implements Validator {

    @Override
    public void validate(Object o, Errors errors) {
        Vaccino vaccino = (Vaccino) o;
        LocalDate dataPrimaDose = vaccino.getDataPrimaDose();
        LocalDate dataSecondaDose = vaccino.getDataSecondaDose();

        if(dataSecondaDose!=null) {
            if(dataSecondaDose.isBefore(dataPrimaDose)) {
                errors.rejectValue("dataSecondaDose","invalid");
            }
        }


    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Vaccino.class.equals(clazz);
    }
}
