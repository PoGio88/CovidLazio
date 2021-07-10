package it.uniroma3.siw.covidLazio.model;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime orarioApertura;

    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime orarioChiusura;

    private String sitoWeb;
    
    @ManyToOne
    private Localita localitaNegozio;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "negozio")
    private List<Prodotto> prodotti;
    
    

}
