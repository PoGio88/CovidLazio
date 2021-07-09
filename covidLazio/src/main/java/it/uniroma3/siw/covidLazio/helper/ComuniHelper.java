package it.uniroma3.siw.covidLazio.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.uniroma3.siw.covidLazio.model.Localita;
import lombok.Data;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.json.JsonParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ComuniHelper {

    public static List<Localita> getComuniLazio() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Comune> comuni = objectMapper.readValue(new FileReader("src/main/resources/static/files/COMUNI_LAZIO.json"), new TypeReference<List<Comune>>() {
        });
        List<Localita> localitaList = new ArrayList<>();
        for (Comune comune : comuni) {
            Localita nuova = new Localita();
            nuova.setNome(comune.nome);
            nuova.setCAP(comune.cap);
            nuova.setProvincia(comune.provincia);
            localitaList.add(nuova);
        }
        return localitaList;
    }

    @Data
    static class Comune {
        String nome;
        String codice;
        String regione;
        String provincia;
        String codiceCatastale;
        String cap;
        Object coordinate;
    }
}
