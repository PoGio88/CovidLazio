package it.uniroma3.siw.covidLazio.model;

import javax.persistence.*;

import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;


@Entity
@Data
public class Utente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String nome;

	private String cognome;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataNascita;

	private String email;

	@OneToOne(cascade = CascadeType.ALL)
	private Vaccino vaccino;

	@ToString.Exclude
	@OneToOne(cascade = CascadeType.ALL)
	private Locale locale;

	@ToString.Exclude
	@ManyToOne
	private Localita localita;

	@OneToOne(mappedBy = "utente",cascade = CascadeType.ALL)
	private Tampone tampone;
}


