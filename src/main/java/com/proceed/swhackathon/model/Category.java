package com.proceed.swhackathon.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Category {
    HAMBURGER,PIZZA,COFFEE,DESSERT,KOREANFOOD,CHINESEFOOD,FLOURBASEDFOOD;

    @JsonCreator
    public static Category from(String s) {
        return Category.valueOf(s.toUpperCase());
    }

}

