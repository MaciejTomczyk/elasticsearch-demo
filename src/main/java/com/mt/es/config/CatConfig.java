package com.mt.es.config;

import com.mt.es.model.Cat;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static com.mt.es.config.CatConstants.BALLET;
import static com.mt.es.config.CatConstants.BLACK;
import static com.mt.es.config.CatConstants.BLUE;
import static com.mt.es.config.CatConstants.BREED_A;
import static com.mt.es.config.CatConstants.BREED_B;
import static com.mt.es.config.CatConstants.BROWN;
import static com.mt.es.config.CatConstants.DANCING;
import static com.mt.es.config.CatConstants.FEMALE;
import static com.mt.es.config.CatConstants.FOOTBALL;
import static com.mt.es.config.CatConstants.GREEN;
import static com.mt.es.config.CatConstants.HOCKEY;
import static com.mt.es.config.CatConstants.MALE;
import static com.mt.es.config.CatConstants.RED;
import static com.mt.es.config.CatConstants.WHITE;


@Configuration
public class CatConfig {


    @Bean
    public List<Cat> getCats() {
        Cat c1 = prepareCat(0L, "A", LocalDate.of(2018, 12, 1).toString(), BLUE.value(), BLACK.value(), MALE.value(), BREED_A.value(), HOCKEY.value());
        Cat c2 = prepareCat(1L, "B", LocalDate.of(2018, 12, 2).toString(), BROWN.value(), WHITE.value(), FEMALE.value(), BREED_A.value(), FOOTBALL.value());
        Cat c3 = prepareCat(2L, "C", LocalDate.of(2018, 11, 3).toString(), GREEN.value(), BROWN.value(), MALE.value(), BREED_A.value(), BALLET.value());
        Cat c4 = prepareCat(3L, "D", LocalDate.of(2017, 10, 4).toString(), BLUE.value(), RED.value(), MALE.value(), BREED_A.value(), HOCKEY.value());
        Cat c5 = prepareCat(4L, "E", LocalDate.of(2017, 10, 5).toString(), BLUE.value(), BLACK.value(), MALE.value(), BREED_A.value(), DANCING.value());
        Cat c6 = prepareCat(5L, "F", LocalDate.of(2017, 12, 6).toString(), GREEN.value(), BLACK.value(), MALE.value(), BREED_A.value(), HOCKEY.value());
        Cat c7 = prepareCat(6L, "G", LocalDate.of(2016, 9, 7).toString(), BROWN.value(), BROWN.value(), MALE.value(), BREED_A.value(), HOCKEY.value());
        Cat c8 = prepareCat(7L, "H", LocalDate.of(2016, 9, 8).toString(), BLUE.value(), WHITE.value(), MALE.value(), BREED_A.value(), FOOTBALL.value());
        Cat c9 = prepareCat(8L, "I", LocalDate.of(2016, 7, 9).toString(), RED.value(), RED.value(), FEMALE.value(), BREED_B.value(), DANCING.value());
        Cat c10 = prepareCat(9L, "J", LocalDate.of(2016, 12, 10).toString(), BLUE.value(), BLACK.value(), FEMALE.value(), BREED_A.value(), BALLET.value());

        return Arrays.asList(c1, c2, c3, c4, c5, c6, c7, c8, c9, c10);
    }

    private Cat prepareCat(Long id, String name, String birthDate, String eyeColour, String furColour, String gender, String breed, String interests) {
        return new Cat(id, name, birthDate, eyeColour, furColour, gender, breed, interests);
    }
}

