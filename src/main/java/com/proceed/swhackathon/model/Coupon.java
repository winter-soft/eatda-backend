package com.proceed.swhackathon.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Coupon extends TimeZone {

    @Id @GeneratedValue
    @Column(name = "coupon_id")
    private Long id;

    @JsonIgnore // 쿠폰코드는 노출되면 안되기 때문에
    private String couponCode; // 쿠폰 코드

    private String couponName; // 쿠폰이름
    private int couponPrice; // 할인가격

    @JsonIgnore // 수량도 노출 X
    private int count; // 수량

    private boolean status; // 사용할 수 있는 쿠폰인지 여부

    private LocalDateTime endAt; // 만료날짜

    public void useCoupon(){
        this.count -= 1;
    }

    // 기한이 지났으면 true, 지나지 않았으면 false
    public boolean couponExpireCheck(){
        return (this.endAt.compareTo(LocalDateTime.now()) >= 0 && this.status) ? false : true;
    }
}
