package it.uniroma3.siw.covidLazio;

import com.fasterxml.jackson.core.JsonProcessingException;
import it.uniroma3.siw.covidLazio.helper.ComuniHelper;
import it.uniroma3.siw.covidLazio.service.ComuniService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileNotFoundException;
import java.io.IOException;

@SpringBootApplication
public class CovidLazioApplication {



	public static void main(String[] args) {
		SpringApplication.run(CovidLazioApplication.class, args);
	}

}
