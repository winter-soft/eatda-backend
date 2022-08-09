package com.proceed.swhackathon.dto.store;

import com.proceed.swhackathon.dto.menu.MenuDTO;
import com.proceed.swhackathon.model.*;
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
public class StoreDTO {
    private Long id;
    private String name; // 매장명
    private int minOrderPrice; // 최소 주문금액
    private String backgroundImageUrl; // 배경 이미지
    private Category category;
    private String infor;
    private User user;
    private List<MenuDTO> menus = new ArrayList<>();
    private int likes = 0;

    public static StoreDTO entityToDTO(Store s){
        return StoreDTO.builder()
                .id(s.getId())
                .name(s.getName())
                .minOrderPrice(s.getMinOrderPrice())
                .category(s.getCategory())
                .infor(s.getInfor())
                .user(s.getUser())
                .backgroundImageUrl(s.getBackgroundImageUrl())
                .menus(s.getMenus().stream().map(MenuDTO::entityToDTO).collect(Collectors.toList()))
                .likes(s.getLikesCount())
                .build();
    }
}
