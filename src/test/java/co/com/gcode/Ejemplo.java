package co.com.gcode;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Ejemplo {

    List<Movie> movies1 = new ArrayList<>();
    List<Movie> movies2 = new ArrayList<>();

    public void llenarListas() {
        Movie movie1 = Movie.builder().name("Volver al futuro").durationInMin(130)
                .score(8.5).director("Robert Zemeckis").build();
        Movie movie2 = Movie.builder().name("Anabelle").durationInMin(110)
                .score(6.5).director("John R. Leonetti").build();
        Movie movie3 = Movie.builder().name("Lord of the Ring").durationInMin(180)
                .score(9.2).director("Peter Jackson").build();
        Movie movie4 = Movie.builder().name("Jumanji").durationInMin(120)
                .score(8.7).director("Jake Kasdan").build();
        Movie movie5 = Movie.builder().name("Cadena de favores").durationInMin(140)
                .score(7.9).director("Mimi Leder").build();

        movies1.add(movie1);
        movies1.add(movie2);
        movies1.add(movie3);

        movies2.add(movie4);
        movies2.add(movie5);
    }

    @Test
    public void mergeType1(){
        llenarListas();

        Flux<Movie> movies = Flux.fromIterable(movies1).mergeWith(Flux.fromIterable(movies2));
        movies.subscribe(System.out::println);
    }

    @Test
    public void mergeType2(){
        llenarListas();

        Flux<Movie> moviesList1 = Flux.fromIterable(movies1);
        Flux<Movie> moviesList2 = Flux.fromIterable(movies2);
        Flux.merge(moviesList1,moviesList2).subscribe(System.out::println);
    }

    @Test
    public void filterDuration(){
        llenarListas();

        Flux<Movie> movies = Flux.fromIterable(movies1).mergeWith(Flux.fromIterable(movies2));

        movies.filter(movie -> movie.getDurationInMin() > 120)
                .subscribe(System.out::println);
    }

    @Test
    public void filterScore(){
        llenarListas();

        Flux<Movie> movies = Flux.fromIterable(movies1).mergeWith(Flux.fromIterable(movies2));

        movies.filter(movie -> movie.getScore() > 8.0)
                .subscribe(System.out::println);
    }

    @Test
    public void filterDirector(){
        llenarListas();

        Flux<Movie> movies = Flux.fromIterable(movies1).mergeWith(Flux.fromIterable(movies2));

        movies.filter(movie -> movie.getDirector().equals("Mimi Leder"))
                .subscribe(System.out::println);
    }

    @Test
    public void allFilters(){
        llenarListas();

        Flux.fromIterable(movies1).mergeWith(Flux.fromIterable(movies2))
                .filter(movie -> movie.getDurationInMin() > 120)
                .filter(movie -> movie.getScore() > 8.0)
                .filter(movie -> movie.getDirector().equals("Peter Jackson"))
                .subscribe(System.out::println);
    }
}
