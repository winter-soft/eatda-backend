package com.proceed.swhackathon.dto.kakaoPay;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class ApproveRequestDTO {
    String cid;
    String tid;
    String partner_order_id;
    String partner_user_id;
    String pg_token;
}
