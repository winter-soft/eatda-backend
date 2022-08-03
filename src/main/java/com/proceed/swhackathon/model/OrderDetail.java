package com.proceed.swhackathon.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.proceed.swhackathon.exception.order.OrderNotFoundException;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class OrderDetail { // 어떤 유저가 어떤 오더에 어떤 음식을 시켰는지

    @Id @GeneratedValue
    @Column(name = "orderDetail_id")
    private Long id;

    private int quantity;
    private int totalPrice;
    @ColumnDefault(value = "true")
    private boolean menuCheck; // true면 체크, false은 체크해제

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userOrderDetail_id")
    @JsonIgnore
    private UserOrderDetail userOrderDetail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    public void calTotalPrice(){
        totalPrice = menu.getPrice() * quantity;
    }

    public void triggerCheck(){
        menuCheck = menuCheck ? false : true;
        userOrderDetail.changeTotalPrice(menuCheck, totalPrice);
    }
}
