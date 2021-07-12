package it.uniroma3.siw.covidLazio.model;

import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
public class Tampone {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataPrenotazione;

    private String esito;

    private float prezzoVendita;

    @ToString.Exclude
    @ManyToOne
    private Farmacia farmacia;

    @ToString.Exclude
    @OneToOne
    private Utente utente;

}
