package it.uniroma3.siw.covidLazio.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.covidLazio.model.Locale;
import it.uniroma3.siw.covidLazio.model.Negozio;
import it.uniroma3.siw.covidLazio.model.Prodotto;

public interface ProdottoRepository extends CrudRepository<Prodotto,Long> {
	
	public List<Prodotto> findByLocale(Locale locale);

	public List<Prodotto> findByNomeContaining(String nome);
}
