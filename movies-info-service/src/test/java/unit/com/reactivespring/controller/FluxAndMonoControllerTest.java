package com.reactivespring.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebFlux;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.test.StepVerifier;

@WebFluxTest(FluxAndMonoController.class)
@AutoConfigureWebFlux
class FluxAndMonoControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @Test
    void flux() {

        webTestClient.
                get()
                .uri("/flux")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Integer.class)
                .hasSize(3);
    }

    @Test
    void flux_approach2() {

        var check =
                webTestClient.
                        get()
                        .uri("/flux")
                        .exchange()
                        .expectStatus().isOk()
                        .returnResult(Integer.class)
                        .getResponseBody();

        StepVerifier.create(check)
                .expectNext(1, 2, 3)
                .verifyComplete();
    }

    @Test
    void flux_approach3() {

        var check =
                webTestClient.
                        get()
                        .uri("/flux")
                        .exchange()
                        .expectStatus().isOk()
                        .expectBodyList(Integer.class)
                        .consumeWith(res -> {
                            var response = res.getResponseBody();
                            assert (response.size() == 3);
                        });

    }


//    test case for stream

    @Test
    void stream_approach2() {

        var check =
                webTestClient.
                        get()
                        .uri("/stream")
                        .exchange()
                        .expectStatus().isOk()
                        .returnResult(Long.class)
                        .getResponseBody();

        StepVerifier.create(check)
                .expectNext(0L, 1L, 2L, 3L)
                .thenCancel()
                .verify();
    }

    @Test
    void flux_concat() {
        var check =
                webTestClient.
                        get()
                        .uri("/flux/concat")
                        .exchange()
                        .expectStatus().isOk()
                        .expectBodyList(Integer.class)
                        .consumeWith(res -> {
                            var response = res.getResponseBody();
                            assert (response.size() == 6);
                        });

    }

    @Test
    void testStreamOdd() {
        FluxExchangeResult<String> result = webTestClient.get()
                .uri("/stream/odd")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .exchange()
                .expectStatus().isOk()
                .returnResult(String.class);

        StepVerifier.create(result.getResponseBody())
                .expectNextMatches(event -> event.matches("\\d{2}:\\d{2}:\\d{2} (Even|Odd)"))
                .expectNextMatches(event -> event.matches("\\d{2}:\\d{2}:\\d{2} (Even|Odd)"))
                .expectNextMatches(event -> event.matches("\\d{2}:\\d{2}:\\d{2} (Even|Odd)"))
                .thenCancel()
                .verify();

    }


}