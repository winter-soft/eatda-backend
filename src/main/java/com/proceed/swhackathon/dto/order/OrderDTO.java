package com.proceed.swhackathon.dto.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.proceed.swhackathon.dto.store.StoreInOrderDTO;
import com.proceed.swhackathon.model.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Long id;
    private OrderStatus orderStatus; // 주문 상태
    private int currentAmount; // 현재 달성 금액
    private LocalDateTime startTime; // 시작시간
    private LocalDateTime endTime; // 마감시간
//    @JsonIgnore
    private StoreInOrderDTO store;
    private Destination destination;
    private List<User> users;

    public static OrderDTO entityToDTO(Order o, List<User> users){
        return OrderDTO.builder()
                .id(o.getId())
                .orderStatus(o.getOrderStatus())
                .currentAmount(o.getCurrentAmount())
                .startTime(o.getStartTime())
                .endTime(o.getEndTime())
                .store(StoreInOrderDTO.entityToDTO(o.getStore()))
                .destination(o.getDestination())
                .users(users)
                .build();
    }
}
