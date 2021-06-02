package com.digital.innovation.one.heroesApi.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import com.digital.innovation.one.heroesApi.models.Hero;
import com.digital.innovation.one.heroesApi.repository.HeroesRepository;
import com.digital.innovation.one.heroesApi.service.HeroesService;

import static com.digital.innovation.one.heroesApi.constants.HeroesConstants.HEROES_ENDPOINT_LOCAL;

@RestController
@Slf4j
public class HeroesController {

    HeroesService heroesService;
    HeroesRepository heroesRepository;

    public HeroesController(HeroesService heroesService, HeroesRepository heroesRepository) {
        this.heroesService = heroesService;
        this.heroesRepository = heroesRepository;
    }

    @GetMapping(HEROES_ENDPOINT_LOCAL)
    @ResponseStatus(HttpStatus.OK)
    public Flux<Hero> getAllItems() {
        log.info("requesting the list off all heroes");
        return heroesService.findAll();

    }


    @GetMapping(HEROES_ENDPOINT_LOCAL + "/{id}")
    public Mono<ResponseEntity<Hero>> findByIdHero(@PathVariable String id) {
        log.info("Requesting the hero with id {}", id);
        return heroesService.findByIdHero(id)
                .map((item) -> new ResponseEntity<>(item, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(HEROES_ENDPOINT_LOCAL)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Hero> createHero(@RequestBody Hero hero) {
        log.info("A new Hero was Created");
        return heroesService.save(hero);

    }

    @DeleteMapping(HEROES_ENDPOINT_LOCAL + "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteByIDHero(@PathVariable String id)  {

        heroesService.deleteByIDHero(id);
        log.info("The hero with id {} was deleted", id);
    }

    @PutMapping(HEROES_ENDPOINT_LOCAL)
    public Mono<ResponseEntity<Hero>> updateHero(@Param("id") String id, @RequestBody Hero hero) {

        return heroesService.updateHero(id, hero)
                .map(item -> new ResponseEntity<>(item, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }
}
