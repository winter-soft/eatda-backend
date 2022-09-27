package com.proceed.swhackathon.dto.kakaoPay;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class CancelRequestDTO {
    String cid;
    String tid;
    String cancel_amount;
    String cancel_tax_free_amount;
}
