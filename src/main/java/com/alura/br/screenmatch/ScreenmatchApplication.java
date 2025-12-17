package com.alura.br.screenmatch;

import com.alura.br.screenmatch.model.DadosEpisodio;
import com.alura.br.screenmatch.model.DadosSerie;
import com.alura.br.screenmatch.model.DadosTemporada;
import com.alura.br.screenmatch.service.ConsumoAPI;
import com.alura.br.screenmatch.service.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
        var api = new ConsumoAPI();
        var jsonSerie = api.obterDados("https://www.omdbapi.com/?t=gilmore+girls&apikey=302825a0");
        var jsonEpisodio = api.obterDados("https://www.omdbapi.com/?t=gilmore+girls&season=1&episode=2&apikey=302825a0");

        ConverteDados conversor = new ConverteDados();

        DadosSerie serie = conversor.obterDados(jsonSerie, DadosSerie.class);
        DadosEpisodio episodio = conversor.obterDados(jsonEpisodio, DadosEpisodio.class);
        System.out.printf("Série: %s%n" +
                "Episodio: %s%n", serie, episodio);

        List<DadosTemporada> temporadas = new ArrayList<>();

        for (int i = 1; i <= serie.totalTemporada(); i++){
            var jsonTemporada = api.obterDados("https://www.omdbapi.com/?t=gilmore+girls" +
                    "&season=" + i + "&apikey=302825a0");
            DadosTemporada temporada = conversor.obterDados(jsonTemporada,DadosTemporada.class);
            temporadas.add(temporada);
        }
        temporadas.forEach(System.out::println);


    }
}
