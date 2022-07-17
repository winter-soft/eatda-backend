package com.proceed.swhackathon.model;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class OrderDetail { // 어떤 유저가 어떤 오더에 어떤 음식을 시켰는지

    @Id
    @GeneratedValue
    private Long id;

    private int quantity;
    private int totalPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;
}
