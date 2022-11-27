package com.proceed.swhackathon.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CouponUse extends TimeZone {

    @Id @GeneratedValue
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY) // 어떤 주문에 쿠폰이 적용되었는지 확인하기 위해서
    @JoinColumn(name = "userOrderDetail_id")
    private UserOrderDetail userOrderDetail;

    @ManyToOne // EAGER (항상 coupon 정보가 필요하므로)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;

    private boolean couponUse; // 쿠폰이 적용 됐는지 아닌지

    // 해당 주문에 쿠폰 적용하기
    public void couponUsing(UserOrderDetail uod){
        this.userOrderDetail = uod;
        this.couponUse = true;
    }
}
