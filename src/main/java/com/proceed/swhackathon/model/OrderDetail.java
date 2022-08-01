package com.proceed.swhackathon.model;

import com.proceed.swhackathon.exception.order.OrderNotFoundException;
import lombok.*;

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
    private boolean menuCheck; // true면 체크, false은 체크해제

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    public void calTotalPrice(){
        totalPrice = menu.getPrice() * quantity;
        order.calCurrentAmount(totalPrice);
    }

    public void cancel(){
        if(order == null){
            throw new OrderNotFoundException();
        }
        order.cancel(totalPrice);
    }

    public void triggerCheck(){
        menuCheck = menuCheck ? false : true;
    }
}
