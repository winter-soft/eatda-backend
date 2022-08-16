package com.proceed.swhackathon.dto.store;

import com.proceed.swhackathon.dto.menu.MenuDTO;
import com.proceed.swhackathon.model.Category;
import com.proceed.swhackathon.model.Order;
import com.proceed.swhackathon.model.Store;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreSearchResponseDTO {
    private Long id;
    private String name; // 매장명
    private int minOrderPrice; // 최소 주문금액
    private String backgroundImageUrl; // 배경 이미지
    private Category category;
    private Order recentlyOrder;
    private String infor;
    private String distnace;
    private int likes = 0;

    public static StoreSearchResponseDTO entityToDTO(Store s){
        return StoreSearchResponseDTO.builder()
                .id(s.getId())
                .name(s.getName())
                .minOrderPrice(s.getMinOrderPrice())
                .category(s.getCategory())
                .infor(s.getInfor())
                .recentlyOrder(s.getRecentlyOrder())
                .distnace(s.getDistance())
                .backgroundImageUrl(s.getBackgroundImageUrl())
                .likes(s.getLikesCount())
                .build();
    }
}
