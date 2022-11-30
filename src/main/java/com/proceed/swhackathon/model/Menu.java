package com.proceed.swhackathon.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "store")
public class Menu extends TimeZone {

    @Id @GeneratedValue
    @Column(name = "menu_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "store_id")
    @JsonIgnore
    private Store store;

    private String name;
    private String imageUrl;
    private int price;

    private boolean visible = true; // 메뉴 보이게할건지 아닌지

    public void removeStore(){
        store = null;
    }
}
