package it.uniroma3.siw.covidLazio.repository;

import it.uniroma3.siw.covidLazio.model.Farmacia;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface FarmaciaRepository extends CrudRepository<Farmacia,Long> {

    public List<Farmacia> findByTamponiDisponibiliGreaterThan(Long disponibili);
}
