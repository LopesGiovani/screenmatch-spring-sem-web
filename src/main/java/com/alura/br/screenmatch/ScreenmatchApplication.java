package com.alura.br.screenmatch;

import com.alura.br.screenmatch.model.DadosSerie;
import com.alura.br.screenmatch.service.ConsumoAPI;
import com.alura.br.screenmatch.service.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
        var api = new ConsumoAPI();
        var json = api.obterDados("https://www.omdbapi.com/?t=gilmore+girls&apikey=302825a0");

        ConverteDados conversor = new ConverteDados();
        DadosSerie serie = conversor.obterDados(json, DadosSerie.class);

        System.out.println(serie);
    }
}
