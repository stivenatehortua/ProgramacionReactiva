package co.com.gcode;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class Operators {

    @Test
    public void multiplyByTen(){
        Flux.range(1,10)
                .map(x -> x * 20)
                .map(y -> y / 10)
                .subscribe(
                        System.out::println
                );
    }

    @Test
    public void mapTest1(){
        Mono.just("stiven")
                .map(String::toUpperCase)
                .subscribe(
                        System.out::println
                );
    }

    @Test
    public void mergeNumbers1() {
        Flux<String> pares = Flux.just("2","4","6","8");
        Flux<String> impares = Flux.just("1","3","5","7","9");

        Flux.merge(pares,impares)
                .subscribe(
                        System.out::println
                );
    }

    @Test
    public void mergeNumbers2(){
        Flux<String> pares = Flux.just("2","4","6","8");
        Flux<String> impares = Flux.just("1","3","5","7","9");

        Flux.mergeOrdered(pares,impares)
                .subscribe(
                        System.out::println
                );
    }

    @Test
    public void flatMapExample1(){
        Flux.just(1,2,3,4,5)
                .flatMap(x -> Flux.just("Hello world " + x))
                .subscribe(System.out::println);
    }

    @Test
    public void filterNumber(){
        Flux.just(0,1,2,3,4,5,6,7,8,9)
                .filter(number -> number%2 == 0)
                .subscribe(System.out::println);
    }

    List<String> cities = Arrays.asList("Chennai","Pune","Mumbai", "Kolkatta");
    @Test
    public void filterTest1(){
        Flux<String> cityFlux = Flux.fromIterable(cities);
        Flux<String> filteredFilterCityFlux = cityFlux.filter(city -> city.startsWith("P"));
        filteredFilterCityFlux.subscribe(System.out::println);
    }

    @AllArgsConstructor
    @Getter
    @ToString
    @EqualsAndHashCode
    class Movie {
        String title;
        int duration;
        double score;
    }
    @Test
    public void zipOperator(){
        Flux<String> titleFlux = Flux.just("El padrino","El lobo de wolf street","Milla 22");
        Flux<Integer> durationFlux = Flux.just(200,205,120);
        Flux<Double> scoreFlux = Flux.just(9.2,8.2,6.1);
        Flux<Movie> moviesFlux = Flux.zip(titleFlux, durationFlux, scoreFlux)
                .flatMap(tuple -> Flux.just(new Movie(tuple.getT1(),tuple.getT2(),tuple.getT3())));
        moviesFlux.subscribe(System.out::println);
    }

    @Test
    public void monoError(){
        Mono<Object> error = Mono.error(new IllegalArgumentException("Stiven -> Illegal Argument Exception"))
                .doOnError(e -> log.error("Error message: {}", e.getMessage()))
                .log();
        error.subscribe(System.out::println);

        /*StepVerifier.create(error)
                .expectError(IllegalArgumentException.class)
                .verify();*/
    }

    @Test
    public void monoOnErrorResume(){
        Mono<Object> error = Mono.error(new IllegalArgumentException("Stiven -> Illegal Argument Exception"))
                .doOnError(e -> log.error("Error message: {}", e.getMessage()))
                .onErrorResume(s -> {
                    log.info("inside on Error resume");
                    return Mono.just("Fixed");
                });

        error.subscribe(System.out::println);
    }

    @Test
    public void monoOnErrorReturn(){
        Mono<Object> error = Mono.error(new IllegalArgumentException("Stiven -> Illegal Argument Exception"))
                .onErrorReturn("Hello")
                .doOnError(e -> log.error("Error message: {}", e.getMessage()))
                .onErrorResume(s -> {
                    log.info("inside on Error resume");
                    return Mono.just("Fixed");
                });

        error.subscribe(System.out::println);
    }

    @Test
    public void testMonoWithStepVerifier(){
        String name = "Stiven Atehortua";
        Mono<String> mono = Mono.just(name).log();
        mono.subscribe();

        StepVerifier.create(mono).expectNext(name).verifyComplete();
    }
}
