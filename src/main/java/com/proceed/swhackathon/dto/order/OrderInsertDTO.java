package com.proceed.swhackathon.dto.order;

import com.proceed.swhackathon.model.Order;
import com.proceed.swhackathon.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderInsertDTO {
    private OrderStatus orderStatus; // 주문 상태
    private int currentAmount; // 현재 달성 금액
    private LocalDateTime startTime; // 시작시간
    private LocalDateTime endTime; // 마감시간
    private Long destination_id;

    public Order dtoToEntity(){
        return Order.builder()
                .orderStatus(this.orderStatus)
                .currentAmount(this.currentAmount)
                .startTime(this.startTime)
                .endTime(this.endTime)
                .build();
    }

    public static OrderInsertDTO entityToDTO(Order o){
        return OrderInsertDTO.builder()
                .orderStatus(o.getOrderStatus())
                .currentAmount(o.getCurrentAmount())
                .startTime(o.getStartTime())
                .endTime(o.getEndTime())
                .destination_id(o.getDestination().getId())
                .build();
    }
}
