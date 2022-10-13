package com.proceed.swhackathon.dto.store;

import com.proceed.swhackathon.dto.menu.MenuDTO;
import com.proceed.swhackathon.model.Category;
import com.proceed.swhackathon.model.Order;
import com.proceed.swhackathon.model.Store;
import com.proceed.swhackathon.model.User;
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
public class StoreInOrderDTO {
    private Long id;
    private String name; // 매장명
    private int minOrderPrice; // 최소 주문금액
    private String backgroundImageUrl; // 배경 이미지
    private String distnace;

    public static StoreInOrderDTO entityToDTO(Store store){
        return StoreInOrderDTO.builder()
                .id(store.getId())
                .name(store.getName())
                .minOrderPrice(store.getMinOrderPrice())
                .backgroundImageUrl(store.getBackgroundImageUrl())
                .distnace(store.getDistance())
                .build();
    }
}
