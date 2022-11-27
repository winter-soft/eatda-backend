package com.proceed.swhackathon.repository;

import com.proceed.swhackathon.model.Coupon;
import com.proceed.swhackathon.model.CouponUse;
import com.proceed.swhackathon.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CouponUseRepository extends JpaRepository<CouponUse, Long> {

    Boolean existsByCouponAndUser(Coupon coupon, User user);

    List<CouponUse> findAllByUserAndCouponUseFalse(User user);
}
