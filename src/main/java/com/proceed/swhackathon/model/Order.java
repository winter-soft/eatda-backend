package com.proceed.swhackathon.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.proceed.swhackathon.exception.order.OrderIllegalArgumentException;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "ORDERS")
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus; // 주문 상태

    private int currentAmount; // 현재 달성 금액

    private LocalDateTime startTime; // 시작시간
    private LocalDateTime endTime; // 마감시간

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_id")
    private Destination destination;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<OrderDetail> orderDetails = new ArrayList<>();

    public void calCurrentAmount(int amount){
        currentAmount += amount;
    }

    public void cancel(int amount){
        if(currentAmount - amount < 0){
            throw new OrderIllegalArgumentException();
        }else{
            currentAmount -= amount;
        }
    }

    public void removeStore(){
        store = null;
    }
}
