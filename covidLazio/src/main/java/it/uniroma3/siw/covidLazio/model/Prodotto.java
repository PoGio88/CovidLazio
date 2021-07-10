package it.uniroma3.siw.covidLazio.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Prodotto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nome;

    private float prezzo;

    private boolean disponibile;

}
