package com.proceed.swhackathon.dto;

import com.proceed.swhackathon.model.Store;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
}
