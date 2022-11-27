package com.proceed.swhackathon.dto.userOrderDetail;

import com.proceed.swhackathon.model.Order;
import com.proceed.swhackathon.model.OrderDetail;
import com.proceed.swhackathon.model.Store;
import com.proceed.swhackathon.model.UserOrderDetail;
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
public class UserOrderDetailResponseDTO {
    private Long id;
    private int totalPrice;
    private Store store;
    private Order order;
    private List<OrderDetail> orderDetails;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;

    public static UserOrderDetailResponseDTO entityToDTO(UserOrderDetail uod){
        return UserOrderDetailResponseDTO.builder()
                .id(uod.getId())
                .totalPrice(uod.getTotalPrice())
                .store(uod.getOrder().getStore())
                .order(uod.getOrder())
                .orderDetails(uod.getOrderDetails())
                .createdTime(uod.getCreatedTime())
                .updatedTime(uod.getUpdatedTime())
                .build();
    }
}
