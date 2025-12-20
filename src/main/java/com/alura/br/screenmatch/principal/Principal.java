package com.alura.br.screenmatch.principal;

import com.alura.br.screenmatch.model.DadosEpisodio;
import com.alura.br.screenmatch.model.DadosSerie;
import com.alura.br.screenmatch.model.DadosTemporada;
import com.alura.br.screenmatch.service.ConsumoAPI;
import com.alura.br.screenmatch.service.ConverteDados;

import javax.sound.midi.Soundbank;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {

    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String APIKEY = "&apikey=302825a0";
    private ConsumoAPI consumo = new ConsumoAPI();
    private ConverteDados conversor = new ConverteDados();
    private Scanner leitura = new Scanner(System.in);

    public void exibeMenu (){
        System.out.println("Digite o nome da série para busca: ");
        var nomeSerie = leitura.nextLine();
        nomeSerie = nomeSerie.replace(" ", "+");

        var json = consumo.obterDados(ENDERECO + nomeSerie + APIKEY);
        DadosSerie dadosSerie = conversor.obterDados(json, DadosSerie.class);
        System.out.println(dadosSerie);

        List<DadosTemporada> temporadas = new ArrayList<>();

        for (int i = 1; i <= dadosSerie.totalTemporada(); i++){
            var jsonTemporada = consumo.obterDados(ENDERECO + nomeSerie +
                    "&season=" + i + APIKEY);
            DadosTemporada temporada = conversor.obterDados(jsonTemporada,DadosTemporada.class);
            temporadas.add(temporada);
        }
        // Imprime todas as temporadas
        temporadas.forEach(System.out::println);

//       Versão imperativa (for tradicional) - substituída por lambdas
//        for (int i = 0; i< dadosSerie.totalTemporada(); i++){
//            List<DadosEpisodio> episodiosTemporada = temporadas.get(i).episodios();
//            for (int j = 0; j < episodiosTemporada.size(); j++){
//                System.out.println(episodiosTemporada.get(j).titulo());
//            }
//        }

        // Itera sobre cada temporada e imprime o título de todos os episódios
        temporadas.forEach(t -> t.episodios().forEach(
                e -> System.out.println(e.titulo())));

        List<DadosEpisodio> dadosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream())
                        .collect(Collectors.toList());
        System.out.println("Top 5");
        dadosEpisodios.stream()
                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
                .limit(5)
                .forEach(System.out::println);

    }
}
