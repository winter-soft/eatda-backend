package com.proceed.swhackathon.repository;

import com.proceed.swhackathon.model.Coupon;
import com.proceed.swhackathon.model.CouponUse;
import com.proceed.swhackathon.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CouponUseRepository extends JpaRepository<CouponUse, Long> {

    @Query("select cu from CouponUse cu where cu.id = :couponUseId")
    CouponUse findByCouponUseId(@Param("couponUseId") Long couponUseId);

    Boolean existsByCouponAndUser(Coupon coupon, User user);

    List<CouponUse> findAllByUserAndCouponUseFalse(User user);
}
