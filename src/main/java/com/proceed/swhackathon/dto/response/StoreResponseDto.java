package com.proceed.swhackathon.dto.response;

import com.proceed.swhackathon.model.Order;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class StoreResponseDto {
    private Long id;
    private String name;
    private int minOrderPrice;
    private String backgroundImageUrl;
}
