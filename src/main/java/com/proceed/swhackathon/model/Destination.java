package com.proceed.swhackathon.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Destination {

    @Id @GeneratedValue
    @Column(name = "destination_id")
    private Long id;

    private String name; // 배송지 이름
}
