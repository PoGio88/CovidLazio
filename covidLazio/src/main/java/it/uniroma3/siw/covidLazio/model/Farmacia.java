package it.uniroma3.siw.covidLazio.model;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@Entity
public class Farmacia extends Locale{

    private Long tamponiDisponibili;

    private float prezzoTampone;

    @OneToMany(mappedBy = "farmacia",cascade = CascadeType.ALL)
    private List<Tampone> tamponiPrenotati;
}
