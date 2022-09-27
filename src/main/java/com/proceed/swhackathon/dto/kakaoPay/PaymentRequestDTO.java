package com.proceed.swhackathon.dto.kakaoPay;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class PaymentRequestDTO {

    String cid;
    String partner_order_id;
    String partner_user_id;
    String item_name;
    String quantity; // Integer
    String total_amount; // Integer
    String tax_free_amount; // Integer
    String approval_url;
    String cancel_url;
    String fail_url;
}
