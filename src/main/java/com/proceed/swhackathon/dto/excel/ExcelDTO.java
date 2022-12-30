package com.proceed.swhackathon.dto.excel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExcelDTO {
    private int id;
    private String store;
    private String date;
    private String menu;
    private int price;
    private int quantity;
    private int totalPrice;
}
