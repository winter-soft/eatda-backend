package com.proceed.swhackathon.model;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Destination {

    @Id @GeneratedValue
    @Column(name = "destination_id")
    private Long id;

    private String name; // 배송지 이름
}
