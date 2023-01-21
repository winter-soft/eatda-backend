package com.proceed.swhackathon.dto.menu;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.proceed.swhackathon.model.Menu;
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
public class MenuDTO {
    private Long id;
    @JsonIgnore
    private Store store;
    private String name;
    private String imageUrl;
    private int price;
    private String menuDetail;
    private boolean visible = true; // 메뉴 보이게할건지 아닌지


    public static MenuDTO entityToDTO(Menu m){
        return MenuDTO.builder()
                .id(m.getId())
                .store(m.getStore())
                .name(m.getName())
                .imageUrl(m.getImageUrl())
                .price(m.getPrice())
                .menuDetail(m.getMenuDetail())
                .visible(true)
                .build();
    }

    public static List<MenuDTO> entityToDTO(List<Menu> menus){
        return menus
                .stream()
                .map(MenuDTO::entityToDTO)
                .collect(Collectors.toList());
    }
}
