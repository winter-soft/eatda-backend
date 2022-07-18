package com.proceed.swhackathon.model;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Menu {

    @Id @GeneratedValue
    @Column(name = "menu_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    private String name;
    private String imageUrl;
    private String price;
}
