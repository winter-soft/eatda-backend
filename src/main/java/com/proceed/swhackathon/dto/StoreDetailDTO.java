package com.proceed.swhackathon.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.proceed.swhackathon.model.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreDetailDTO {
    private Long id;
    private OrderStatus orderStatus; // 주문 상태
    private int currentAmount; // 현재 달성 금액
    private LocalDateTime startTime; // 시작시간
    private LocalDateTime endTime; // 마감시간
    private StoreDTO store;
    private Destination destination;
    private List<OrderDetail> orderDetails = new ArrayList<>();

    public static StoreDetailDTO entityToDTO(Order o){
        return StoreDetailDTO.builder()
                .id(o.getId())
                .destination(o.getDestination())
                .orderStatus(o.getOrderStatus())
                .currentAmount(o.getCurrentAmount())
                .startTime(o.getStartTime())
                .endTime(o.getEndTime())
                .build();
    }
}
