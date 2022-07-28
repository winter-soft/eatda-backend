package com.proceed.swhackathon.dto;

import com.proceed.swhackathon.model.Likes;
import com.proceed.swhackathon.model.Menu;
import com.proceed.swhackathon.model.Order;
import com.proceed.swhackathon.model.Store;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreDTO {
    private Long id;
    private String name; // 매장명
    private int minOrderPrice; // 최소 주문금액
    private String backgroundImageUrl; // 배경 이미지
    private List<MenuDTO> menus = new ArrayList<>();
    private int likes = 0;

    public static StoreDTO entityToDTO(Store s){
        return StoreDTO.builder()
                .id(s.getId())
                .name(s.getName())
                .minOrderPrice(s.getMinOrderPrice())
                .backgroundImageUrl(s.getBackgroundImageUrl())
                .likes(s.getLikesCount())
                .build();
    }
}
