package com.digital.innovation.one.heroesApi.repository;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import com.digital.innovation.one.heroesApi.models.Hero;

@EnableScan
public interface HeroesRepository extends CrudRepository<Hero, String>{
}
