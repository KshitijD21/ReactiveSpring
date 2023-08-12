package com.learnreactiveprogramming.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;


class FluxServices {
    public Flux<String> nameFlux() {
        return Flux.fromIterable(List.of("mike", "harvey", "Kshitij"));
    }


    public Flux<String> nameFlux_map() {
        return Flux.fromIterable(List.of("mike", "harvey", "kshitij"))
                .map(String::toUpperCase);
    }

    //    map operator
    public Flux<String> nameFlux_mapImmutable() {
        var nameFlux = Flux.fromIterable(List.of("mike", "harvey", "kshitij"));
        nameFlux.map(String::toUpperCase);
        return nameFlux;
    }

    //    filter Operator
    public Flux<String> nameFlux_filter(int stringLength) {
        return Flux.fromIterable(List.of("mike", "harvey", "kshitij"))
                .filter(s -> s.length() > stringLength)
                .map(s -> s.length() + "-" + s);
    }


    //    filter map operator
    public Flux<String> nameFlux_filterMap(int stringLength) {
        return Flux.fromIterable(List.of("mike", "harvey", "kshitij"))
                .filter(s -> s.length() > stringLength)
                .flatMap(s -> splitString(s));
    }

    private Flux<String> splitString(String s) {
        var charArray = s.split("");
        return Flux.fromArray(charArray);
    }


    //    filter map asyn
    public Flux<String> nameFlux_filtermap_asyn(int stringLength) {
        return Flux.fromIterable(List.of("mike", "harvey", "kshitij"))
                .filter(s -> s.length() > stringLength)
                .flatMap(s -> splitString_delay(s))
                .log();
    }

    public Flux<String> splitString_delay(String s) {

        var charArr = s.split("");
        return Flux.fromArray(charArr)
                .delayElements(Duration.ofMillis(1000));
    }

    // flat map many
    public Flux<String> nameFlux_flatMapMany() {
        return Mono.just("kshi")
                .flatMapMany(this::splitString);
    }
}


class MonoServices extends FluxServices {
    public Mono<String> nameMono() {
        return Mono.just("Alex");
    }

    public Mono<List<String>> nameMono_flatmap(int stringLength) {
        return Mono.just("kshi")
                .map(String::toUpperCase)
                .filter(s -> s.length() > stringLength)
                .flatMap(this::splitStringMomo);
    }

    private Mono<List<String>> splitStringMomo(String s) {
        var charArr = s.split("");
        var charList = List.of(charArr);
        return Mono.just(charList);
    }

    //zip
    public Flux<String> explore_zip() {
        var abc = Flux.just("A", "B", "C");

        var def = Flux.just("D", "E", "F");

        var _123f = Flux.just("1", "2", "3");

        var _456f = Flux.just("4", "5", "6");

        return Flux.zip(abc, def, _123f, _456f)
                .map(t4 -> t4.getT1() + t4.getT2() + t4.getT3() + t4.getT4());
    }
}

class FluxMono extends MonoServices {

}

public class FluxAndMonoGeneratorService {

    public static void main(String[] args) throws InterruptedException {

        FluxMono service = new FluxMono();

//        CountDownLatch latch = new CountDownLatch(1);
//
//        service.nameFlux_flatMapMany().subscribe(
//                name -> {
//                    System.out.println(name);
//                },
//                null,
//                latch::countDown
//        );
//        latch.await();

        service.explore_zip().subscribe(
                name -> {
                    System.out.println(name);
                }
        );
    }


}
