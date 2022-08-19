package com.proceed.swhackathon.dto.userOrderDetail;

import com.proceed.swhackathon.model.OrderDetail;
import com.proceed.swhackathon.model.Store;
import com.proceed.swhackathon.model.UserOrderDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserOrderDetailResponseDTO {
    private Long id;
    private int totalPrice;
    private Store store;
    private List<OrderDetail> orderDetails;

    public static UserOrderDetailResponseDTO entityToDTO(UserOrderDetail uod){
        return UserOrderDetailResponseDTO.builder()
                .id(uod.getId())
                .totalPrice(uod.getTotalPrice())
                .store(uod.getOrder().getStore())
                .orderDetails(uod.getOrderDetails())
                .build();
    }
}
