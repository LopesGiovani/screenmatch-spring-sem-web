# Screenmatch

Aplicação Java (Spring Boot) que consome a API pública OMDb para buscar informações de séries e imprime os dados no console.

## Visão Geral
- Busca uma série na OMDb e converte o JSON em um `record` Java.
- Executa como aplicação de linha de comando (sem camada web).
- Ponto de entrada em `src/main/java/com/alura/br/screenmatch/ScreenmatchApplication.java`.

## Tecnologias
- Java 17
- Spring Boot 4.0.0 (`spring-boot-starter`)
- Maven Wrapper (`mvnw` / `mvnw.cmd`)
- Jackson (`jackson-databind`) para desserialização JSON

## Estrutura do Projeto
- `src/main/java/com/alura/br/screenmatch/ScreenmatchApplication.java`: inicialização e fluxo principal
- `src/main/java/com/alura/br/screenmatch/service/ConsumoAPI.java`: chamada HTTP para OMDb
- `src/main/java/com/alura/br/screenmatch/service/ConverteDados.java`: conversão de JSON em objeto
- `src/main/java/com/alura/br/screenmatch/model/DadosSerie.java`: modelo (`record`) da série
- `src/main/resources/application.properties`: nome da aplicação
- `pom.xml`: dependências e plugins

## Requisitos
- JDK 17 instalado e configurado no `PATH`
- A internet liberada para acesso à OMDb
- Opcional: Maven instalado (não é necessário por conta do Maven Wrapper)

## Como Executar (Windows)
1. Clonar/abrir o projeto.
2. Rodar diretamente com o Maven Wrapper:
   ```bash
   mvnw.cmd spring-boot:run
   ```
3. Ou gerar o JAR e executar:
   ```bash
   mvnw.cmd clean package
   java -jar target/screenmatch-0.0.1-SNAPSHOT.jar
   ```

## Configuração da OMDb
- A URL consultada está definida em `src/main/java/com/alura/br/screenmatch/ScreenmatchApplication.java:20`.
- Para mudar a série ou a `apikey`, altere a linha onde a URL é montada. Exemplo:
  ```java
  var json = api.obterDados("https://www.omdbapi.com/?t=gilmore+girls&apikey=SEU_API_KEY");
  ```
- Campos mapeados em `DadosSerie`:
  - `Title` → `titulo` (`src/main/java/com/alura/br/screenmatch/model/DadosSerie.java:7`)
  - `totalSeasons` → `totalTemporada` (`src/main/java/com/alura/br/screenmatch/model/DadosSerie.java:8`)
  - `imdbRating` → `avaliacao` (`src/main/java/com/alura/br/screenmatch/model/DadosSerie.java:9`)

## Saída Esperada
Ao executar, o programa imprime o objeto `DadosSerie` no console, por exemplo:
```
DadosSerie[titulo=Gilmore Girls, totalTemporada=7, avaliacao=8.1]
```

## Testes
- Executa os testes com:
  ```bash
  mvnw.cmd test
  ```
- Teste de contexto em `src/test/java/com/alura/br/screenmatch/ScreenmatchApplicationTests.java`.

## Observações
- A `apikey` da OMDb no código é apenas para exemplo. Recomenda-se usar a sua própria chave.
- Para externalizar a chave, você pode ler de variável de ambiente ou arquivo de configuração em futuras evoluções.
