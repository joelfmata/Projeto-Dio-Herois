package com.digital.innovation.one.heroesApi.service;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import com.digital.innovation.one.heroesApi.models.Hero;
import com.digital.innovation.one.heroesApi.repository.HeroesRepository;

import java.util.Optional;

@Service
public class HeroesService {

    private final HeroesRepository heroesRepository;

    public HeroesService(HeroesRepository heroesRepository) {
        this.heroesRepository = heroesRepository;
    }

    public Flux<Hero> findAll(){

        return Flux.fromIterable(this.heroesRepository.findAll());
    }

    public  Mono<Hero> findByIdHero(String id){

        return  Mono.justOrEmpty(this.heroesRepository.findById(id));
    }


    public Mono<Hero> save(Hero hero){
        return  Mono.justOrEmpty(this.heroesRepository.save(hero));
    }

    public Mono<Boolean> deleteByIDHero(String id) {
        heroesRepository.deleteById(id);
        return Mono.just(true);

    }

    public Mono<Hero> updateHero(String id, Hero hero){

        Optional<Hero> heroToUpdate = heroesRepository.findById(id);

        if(heroToUpdate.isPresent()) {
            hero.setId(id);
            this.heroesRepository.save(hero);
            return  Mono.just(hero);

        } else {
            return  Mono.empty();
        }

    }
}
