package it.uniroma3.siw.covidLazio.model;
import lombok.Data;

import javax.persistence.*;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Inheritance
public abstract class Locale {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nome;

    private String giornoChiusura;

    private LocalDateTime orarioApertura;

    private LocalDateTime orarioChiusura;

    private URI sitoWeb;

    @OneToMany
    private List<Prodotto> prodotti;

}
