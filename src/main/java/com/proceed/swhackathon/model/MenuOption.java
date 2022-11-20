package com.proceed.swhackathon.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MenuOption {

    @Id @GeneratedValue
    @Column(name = "menuOption_id")
    private Long id;

    @Column(nullable = false)
    private String optionName;

    @Column(nullable = false)
    private int optionPrice;

    private Long menu_id; // Menu (외래키)
}
