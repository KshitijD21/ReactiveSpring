package com.learnreactiveprogramming.service;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import java.util.List;

class FluxAndMonoGeneratorServiceTest {

    FluxMono service = new FluxMono();


//    @Test
//    void nameFlux() {
//
//      var nameFlux =   service.nameFlux();
//
//        StepVerifier.create(nameFlux)
//                .expectNext("mike","harvey","Kshitij")
//                .verifyComplete();
//    }
//
//    @Test
//    void nameFlux_map() {
//        var nameFlux = service.nameFlux_map();
//
//        StepVerifier.create(nameFlux)
//                .expectNext("MIKE","HARVEY","KSHITIJ")
//                .verifyComplete();
//    }

    @Test
    void nameFlux_mapImmutable() {
        var nameFlux = service.nameFlux_mapImmutable();

        StepVerifier.create(nameFlux)
                .expectNext("mike", "harvey", "kshitij")
                .verifyComplete();
    }

    @Test
    void nameFlux_filter() {

        int stringLength = 4;

        var nameFlux = service.nameFlux_filter(stringLength);

        StepVerifier.create(nameFlux)
                .expectNext("harvey", "kshitij")
                .verifyComplete();


    }

    @Test
    void nameFlux_filterMap() {
        int stringLength = 4;

        var nameFlux = service.nameFlux_filterMap(stringLength);

        StepVerifier.create(nameFlux)
                .expectNext("h", "a", "r", "v", "e", "y", "k", "s", "h", "i", "t", "i", "j")
                .verifyComplete();
    }

    @Test
    void nameFlux_filtermap_asyn() {

        int stringLength = 4;

        var nameFlux = service.nameFlux_filtermap_asyn(stringLength);

        StepVerifier.create(nameFlux)
                .expectNextCount(13)
                .verifyComplete();
    }

    @Test
    void nameMono_flatmap() {
        var nameFlux = service.nameMono_flatmap(3);
        StepVerifier.create(nameFlux)
                .expectNext(List.of("K", "S", "H", "I"))
                .verifyComplete();
    }
}