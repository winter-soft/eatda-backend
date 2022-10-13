package com.proceed.swhackathon.dto.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.proceed.swhackathon.dto.store.StoreInOrderDTO;
import com.proceed.swhackathon.model.Destination;
import com.proceed.swhackathon.model.Order;
import com.proceed.swhackathon.model.OrderStatus;
import com.proceed.swhackathon.model.Store;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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

    public static OrderDTO entityToDTO(Order o){
        return OrderDTO.builder()
                .id(o.getId())
                .orderStatus(o.getOrderStatus())
                .currentAmount(o.getCurrentAmount())
                .startTime(o.getStartTime())
                .endTime(o.getEndTime())
                .store(StoreInOrderDTO.entityToDTO(o.getStore()))
                .destination(o.getDestination())
                .build();
    }
}
