package com.proceed.swhackathon.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class OrderDetailOption {

    @Id @GeneratedValue
    @Column(name = "orderDetailOption_id")
    private Long id;

    // MenuOption
    @ManyToOne // EAGER
    @JoinColumn(name = "menuOption_id")
    private MenuOption menuOption;

    // OrderDetail
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY) // 무한루프 방지
    @JoinColumn(name = "orderDetail_id")
    private OrderDetail orderDetail;

}
