package it.uniroma3.siw.covidLazio.repository;

import it.uniroma3.siw.covidLazio.model.Locale;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface LocaleRepository extends CrudRepository<Locale,Long> {

    public Optional<Locale> findById(Long id);

}
