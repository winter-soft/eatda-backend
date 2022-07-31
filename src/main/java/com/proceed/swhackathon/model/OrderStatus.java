package com.proceed.swhackathon.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum OrderStatus {
    WAITING,ACCEPT,SHIPPING,COMPLETE,CANCEL;

    @JsonCreator
    public static OrderStatus from(String s){
        return OrderStatus.valueOf(s.toUpperCase());
    }
}
