package com.proceed.swhackathon.dto.userOrderDetail;

import com.proceed.swhackathon.model.MenuOption;
import com.proceed.swhackathon.model.OrderDetailOption;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BossUserOrderDetailDTO {
    private String menuName;
    private int quantity;
    private int totalPrice;
    private List<OrderDetailOption> orderDetailOptions;
}
