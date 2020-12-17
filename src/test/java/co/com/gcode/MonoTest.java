package co.com.gcode;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Arrays;

public class MonoTest {

    @Test
    public void monoTest1(){
        Mono.just("hola mundo")
                .subscribe(
                        System.out::println
                );
    }

    @Test
    public void fluxTest1(){
        Flux.just(1,2,3,4,5)
                .subscribe(
                        System.out::println
                );
    }

    @Test
    public void fluxTest2(){
        Flux.range(1,20)
                .subscribe(
                        System.out::println
                );
    }

    @Test
    public void fluxTest3(){
        Flux.range(1,20)
                .take(5)
                .subscribe(
                        System.out::println
                );
    }

    @Test
    public void fluxTest4() throws InterruptedException {
        Flux.interval(Duration.ofSeconds(1))
                .log()
                .take(5)
                .subscribe(
                        System.out::println
                );
        Thread.sleep(6000);
    }

    @Test
    public void fluxTest5(){
        Flux.fromIterable(Arrays.asList(1,2,3,4,5))
                .subscribe(
                        System.out::println
                );
    }
}
