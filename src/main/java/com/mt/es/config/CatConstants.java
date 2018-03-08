package com.mt.es.config;

public enum CatConstants {

    ASTERISK("*"),
    BALLET("ballet"),
    BIRTH_DATE("birthDate"),
    BLACK("black"),
    BLUE("blue"),
    BREED_A("A"),
    BREED_B("B"),
    BROWN("brown"),
    CAT("cat"),
    CATS("cats"),
    DANCING("dancing"),
    DATE("date"),
    EYE_COLOUR("eyeColour"),
    FEMALE("female"),
    FOOTBALL("football"),
    FUR_COLOUR("furColour"),
    GREEN("green"),
    HOCKEY("hockey"),
    ID("id"),
    KEYWORD_WITH_DOT(".keyword"),
    KEYWORD("keyword"),
    MALE("male"),
    NAME("name"),
    PUT("PUT"),
    RED("red"),
    SEMICOLON(":"),
    SLASH("/"),
    TEXT("text"),
    WHITE("white");

    private final String value;

    CatConstants(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}

