package com.proceed.swhackathon.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Payment extends TimeZone {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String payment_id;

    private String user_id; // User (외래키)
    private Long order_id; // Order (외래키)

    private String version;
    private String paymentKey;
    private String type;
    private String orderId;
    private String orderName;
    private String mId;
    private String currency;
    private String method;
    private int totalAmount;
    private int balanceAmount;
    private String status;
    private String transactionKey;
    private String lastTransactionKey;
    private int suppliedAmount;
    private int vat;
}