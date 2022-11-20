package com.proceed.swhackathon.dto.orderDetail;

import com.proceed.swhackathon.dto.menuOption.MenuOptionDTO;
import com.proceed.swhackathon.model.MenuOption;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailInsertDTO {
    private int quantity;
    private List<MenuOptionDTO> menuOptions;
}
