package com.digital.innovation.one.heroesApi.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.digital.innovation.one.heroesApi.models.Hero;
import org.springframework.web.reactive.function.BodyInserters;

import static com.digital.innovation.one.heroesApi.constants.HeroesConstants.HEROES_ENDPOINT_LOCAL;

@RunWith(SpringRunner.class)
@DirtiesContext
@AutoConfigureWebTestClient
@SpringBootTest
public class HeroesControllerTest {

    @Autowired
    WebTestClient webTestClient;

   @Test
    public void testGetAllHeroes(){

        webTestClient.get().uri(HEROES_ENDPOINT_LOCAL)
                .exchange()
                .expectStatus().isOk()
                .expectBody();


    }

    @Test
    public void testGetOneHeroById(){

        webTestClient.get().uri(HEROES_ENDPOINT_LOCAL.concat("/{id}"),"1")
                .exchange()
                .expectStatus().isOk()
                .expectBody();


    }

    @Test
    public void TestGetOneHeroByIDNotFound(){

        webTestClient.get().uri(HEROES_ENDPOINT_LOCAL.concat("/{id}"),"100")
                .exchange()
                .expectStatus().isNotFound();

    }

    @Test
    public void TestPostNewHero(){

        Hero heroTest = new Hero();
        heroTest.setName("Teste");
        heroTest.setUniverse("universo do teste");
        heroTest.setPlace_of_origin("testandolandia");
        heroTest.setIdentity_is_secret(false);

        webTestClient.post().uri(HEROES_ENDPOINT_LOCAL)
                .body(BodyInserters.fromValue(heroTest))
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.id").isNotEmpty()
                .jsonPath("$.name").isEqualTo(heroTest.getName())
                .jsonPath("$.universe").isEqualTo(heroTest.getUniverse())
                .jsonPath("$.place_of_origin").isEqualTo(heroTest.getPlace_of_origin())
                .jsonPath("$.identity_is_secret").isEqualTo(heroTest.getIdentity_is_secret());

    }

    @Test
    public void TestDeleteHero(){

        webTestClient.delete().uri(HEROES_ENDPOINT_LOCAL.concat("/{id}"),"2")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Void.class);

    }

    @Test
    public void TestPutToUpdateHero(){

        String idTest = "1";
        Hero heroTest = new Hero();
        heroTest.setName("Tempestade");
        heroTest.setUniverse("universo do teste");
        heroTest.setPlace_of_origin("Morocco");
        heroTest.setIdentity_is_secret(false);

        webTestClient.put()
                .uri(uriBuilder ->
                        uriBuilder
                                .path(HEROES_ENDPOINT_LOCAL)
                                .queryParam("id", idTest)
                                .build())
                .body(BodyInserters.fromValue(heroTest))
                .exchange()
                .expectStatus().isOk()
                .expectBody();
    }

    @Test
    public void TestPutToUpdateHeroNotFound(){

        String idTest = "100";
        Hero heroTest = new Hero();
        heroTest.setName("Teste");
        heroTest.setUniverse("universo do teste");
        heroTest.setPlace_of_origin("testandolandia");
        heroTest.setIdentity_is_secret(false);

        webTestClient.put()
                .uri(uriBuilder -> uriBuilder
                                .path(HEROES_ENDPOINT_LOCAL)
                                .queryParam("id", idTest)
                                .build()
                )
                .body(BodyInserters.fromValue(heroTest))
                .exchange()
                .expectStatus().isNotFound();
    }

}
