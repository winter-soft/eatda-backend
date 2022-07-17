package com.proceed.swhackathon.model;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
public class Destination {

    @Id
    @GeneratedValue
    private Long id;

    private String name; // 배송지 이름
}
