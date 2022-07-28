package com.proceed.swhackathon.dto;

import com.proceed.swhackathon.model.Menu;
import com.proceed.swhackathon.model.Store;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuDTO {
    private Long id;
    private Store store;
    private String name;
    private String imageUrl;
    private int price;

    public static MenuDTO entityToDTO(Menu m){
        return MenuDTO.builder()
                .id(m.getId())
                .store(m.getStore())
                .name(m.getName())
                .imageUrl(m.getImageUrl())
                .price(m.getPrice())
                .build();
    }

    public static List<MenuDTO> entityToDTO(List<Menu> menus){
        List<MenuDTO> menuDTOList = new ArrayList<>();
        for(Menu m : menus){
            MenuDTO menuDTO = MenuDTO.entityToDTO(m);
            menuDTOList.add(menuDTO);
        }
        return menuDTOList;
    }
}
