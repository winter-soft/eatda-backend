package com.proceed.swhackathon.dto.store;

import com.proceed.swhackathon.dto.menu.MenuDTO;
import com.proceed.swhackathon.model.Category;
import com.proceed.swhackathon.model.Store;
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
public class StoreUpdateDTO {
    private String name; // 매장명
    private int minOrderPrice; // 최소 주문금액
    private String backgroundImageUrl; // 배경 이미지
    private Category category;
    private String infor;

    public static StoreUpdateDTO entityToDTO(Store s){
        return StoreUpdateDTO.builder()
                .name(s.getName())
                .minOrderPrice(s.getMinOrderPrice())
                .backgroundImageUrl(s.getBackgroundImageUrl())
                .category(s.getCategory())
                .infor(s.getInfor())
                .build();
    }
}
