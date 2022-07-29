package com.proceed.swhackathon.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Role {
    USER, BOSS, ADMIN;

    @JsonCreator
    public static Role from(String s){
        return Role.valueOf(s.toUpperCase());
    }
}
