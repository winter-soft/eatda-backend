package com.proceed.swhackathon.dto.orderDetail;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.proceed.swhackathon.model.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailDTO {
    private Long id;
    private int quantity;
    private int totalPrice;
    private boolean menuCheck = true; // true면 체크, false은 체크해제
    private User user;
//    @JsonIgnore
    private Order order;
    private Menu menu;
    private List<OrderDetailOption> orderDetailOptions = new ArrayList<>();

    public OrderDetail dtoToEntity(){
        return OrderDetail.builder()
                .quantity(quantity)
                .totalPrice(totalPrice)
                .menuCheck(menuCheck)
                .user(user)
                .order(order)
                .menu(menu)
                .build();
    }

    public static OrderDetailDTO entityToDTO(OrderDetail od){
        return OrderDetailDTO.builder()
                .id(od.getId())
                .quantity(od.getQuantity())
                .totalPrice(od.getTotalPrice())
                .menuCheck(od.isMenuCheck())
                .user(od.getUser())
                .order(od.getOrder())
                .menu(od.getMenu())
                .orderDetailOptions(od.getOrderDetailOptions())
                .build();
    }
}
