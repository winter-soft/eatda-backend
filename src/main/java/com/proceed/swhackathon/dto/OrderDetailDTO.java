package com.proceed.swhackathon.dto;

import com.proceed.swhackathon.model.Menu;
import com.proceed.swhackathon.model.Order;
import com.proceed.swhackathon.model.OrderDetail;
import com.proceed.swhackathon.model.User;
import com.proceed.swhackathon.service.OrderDetailService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailDTO {
    private Long id;
    private int quantity;
    private int totalPrice;
    private boolean menuCheck; // true면 체크, false은 체크해제
    private User user;
    private Order order;
    private Menu menu;

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
                .build();
    }
}
