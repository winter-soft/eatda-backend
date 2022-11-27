package com.proceed.swhackathon.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MenuOption extends TimeZone {

    @Id @GeneratedValue
    @Column(name = "menuOption_id")
    private Long id;

    @Column(nullable = false)
    private String optionName;

    @Column(nullable = false)
    private int optionPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menuOptionTitle_id")
    @JsonIgnore
    private MenuOptionTitle menuOptionTitle;

}
