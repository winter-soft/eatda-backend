package com.proceed.swhackathon.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MenuOptionTitle extends TimeZone { // 하나의 메뉴에 종속되지 않는다.
    @Id @GeneratedValue
    @Column(name = "menuOptionTitle_id")
    private Long id;

    private String titleName; // 옵션의 대표이름

    private boolean multipleCheck; // 옵션 다중선택 가능여부

    @OneToMany(mappedBy = "menuOptionTitle")
    private List<MenuOption> menuOptionList = new ArrayList<>();

}
