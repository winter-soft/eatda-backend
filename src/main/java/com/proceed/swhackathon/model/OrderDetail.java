package com.proceed.swhackathon.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.proceed.swhackathon.exception.order.OrderNotFoundException;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class OrderDetail extends TimeZone { // 어떤 유저가 어떤 오더에 어떤 음식을 시켰는지

    @Id @GeneratedValue
    @Column(name = "orderDetail_id")
    private Long id;

    private int quantity;
    private int totalPrice;
    @ColumnDefault(value = "true")
    private boolean menuCheck = true; // true면 체크, false은 체크해제

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

    @OneToMany(mappedBy = "orderDetail", fetch = FetchType.EAGER)
    private List<OrderDetailOption> orderDetailOptions = new ArrayList<>();

    public void calTotalPrice(){
        int totalOptionPrice = 0; // 옵션가격
        if(orderDetailOptions != null) { // 옵션이 존재할경우
            for(OrderDetailOption odo : orderDetailOptions){
                totalOptionPrice += odo.getMenuOption().getOptionPrice();
            }
        }
        totalPrice = (menu.getPrice() + totalOptionPrice) * quantity;
    }

    public boolean triggerCheck(){
        menuCheck = menuCheck ? false : true;
        if(userOrderDetail != null) userOrderDetail.changeTotalPrice(menuCheck, totalPrice);
        return menuCheck;
    }
}
