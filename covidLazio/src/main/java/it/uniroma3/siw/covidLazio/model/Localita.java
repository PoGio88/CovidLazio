package it.uniroma3.siw.covidLazio.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Localita {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nome;

    private String provincia;

    private String CAP;

    //@OneToMany(mappedBy = "localita")
    //private List<Utente> utenti;
}
