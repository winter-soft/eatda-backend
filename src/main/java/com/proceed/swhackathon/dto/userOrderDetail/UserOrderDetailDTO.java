package com.proceed.swhackathon.dto.userOrderDetail;

import com.proceed.swhackathon.model.Order;
import com.proceed.swhackathon.model.OrderDetail;
import com.proceed.swhackathon.model.UserOrderDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserOrderDetailDTO {
    private Long id;
    private int totalPrice;
    private List<OrderDetail> orderDetails;

    public static UserOrderDetailDTO entityToDTO(UserOrderDetail uod){
        return UserOrderDetailDTO.builder()
                .id(uod.getId())
                .totalPrice(uod.getTotalPrice())
                .orderDetails(uod.getOrderDetails())
                .build();
    }
}
