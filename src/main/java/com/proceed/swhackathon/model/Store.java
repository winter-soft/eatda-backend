package com.proceed.swhackathon.model;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Store {

    @Id
    @GeneratedValue
    private Long id;

    private String name; // 매장명
    private int minOrderPrice; // 최소 주문금액
    private String backgroundImageUrl; // 배경 이미지

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    private List<Likes> likes = new ArrayList<>();
}
