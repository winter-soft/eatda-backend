package com.proceed.swhackathon.dto.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentReqDTO {
    private Long order_id;
    private String order_name;
    private int amount;
}
