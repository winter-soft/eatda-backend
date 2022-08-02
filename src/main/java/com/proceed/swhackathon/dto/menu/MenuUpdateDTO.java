package com.proceed.swhackathon.dto.menu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuUpdateDTO {
    private String name;
    private String imageUrl;
    private int price;
}
