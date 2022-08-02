package com.proceed.swhackathon.dto.menu;

import com.proceed.swhackathon.model.Menu;
import com.proceed.swhackathon.model.Store;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuInsertDTO {
    private String name;
    private String imageUrl;
    private int price;

    public Menu dtoToEntity(){
        return Menu.builder()
                .name(name)
                .imageUrl(imageUrl)
                .price(price)
                .build();
    }
}
