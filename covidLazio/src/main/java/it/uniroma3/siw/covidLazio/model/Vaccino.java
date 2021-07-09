package it.uniroma3.siw.covidLazio.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Entity
public class Vaccino {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String tipologia;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataPrimaDose;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataSecondaDose;
}
