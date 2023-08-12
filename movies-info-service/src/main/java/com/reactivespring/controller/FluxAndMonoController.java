package com.reactivespring.controller;


import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
public class FluxAndMonoController {

    @GetMapping("/flux")
    public Flux<Integer> flux() {
        return Flux.just(1, 2, 3)
                .log();
    }

    @GetMapping("/mono")
    public Mono<String> helloMono() {
        return Mono.just("Hello world")
                .log();
    }

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Long> stream() {
        return Flux.interval(Duration.ofSeconds(1));
    }

    // stream odd numbers along with current time in hh:mm:ss format every 3 second

    @GetMapping(value = "/stream/odd", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamOdd() {
        var currentDateTime = java.time.LocalDateTime.now();
        var formatter = java.time.format.DateTimeFormatter.ofPattern("hh:mm:ss");
        var currentDateTimeFormatted = formatter.format(currentDateTime);
        return Flux.interval(Duration.ofSeconds(3))
                .map(i -> (i % 2 == 0) ? "Even" : "Odd")
                .map(i -> formatter.format(java.time.LocalDateTime.now()) + " " + i);
    }


    @GetMapping("/flux/concat")
    public Flux<Integer> concat() {
        return Flux.just(1, 2, 3)
                .concatWith(Flux.just(4, 5, 6))
                .log();
    }

    @GetMapping("/flux/hello")
    public Flux<Integer> hello() {
        return Flux.just(1, 2, 3)
                .concatWith(Flux.just(4, 5, 6))
                .log();
    }

}