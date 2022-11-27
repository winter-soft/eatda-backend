package com.proceed.swhackathon.service;

import com.proceed.swhackathon.dto.ResponseDTO;
import com.proceed.swhackathon.exception.Message;
import com.proceed.swhackathon.exception.coupon.CouponCodeInCorrectException;
import com.proceed.swhackathon.exception.coupon.CouponDuplicatedException;
import com.proceed.swhackathon.exception.coupon.CouponExpiredException;
import com.proceed.swhackathon.exception.user.UserNotFoundException;
import com.proceed.swhackathon.model.Coupon;
import com.proceed.swhackathon.model.CouponUse;
import com.proceed.swhackathon.model.User;
import com.proceed.swhackathon.repository.CouponRepository;
import com.proceed.swhackathon.repository.CouponUseRepository;
import com.proceed.swhackathon.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CouponService {
    private final UserRepository userRepository;
    private final CouponRepository couponRepository;
    private final CouponUseRepository couponUseRepository;

    @Transactional
    public ResponseDTO<?> registerCoupon(String userId, String couponCode) {
        User user = userRepository.findById(userId).orElseThrow(() -> {
            log.warn("{}", Message.USER_NOT_FOUND);
            throw new UserNotFoundException();
        });

        Coupon coupon = couponRepository.findByCouponCode(couponCode).orElseThrow(() -> {
            log.warn("{}", Message.COUPON_CODE_INCORRECT);
            throw new CouponCodeInCorrectException();
        });

        // 쿠폰 만료일이 지났을 경우
        if(coupon.couponExpireCheck()){
            log.warn("{}", Message.COUPON_EXPIRED);
            throw new CouponExpiredException();
        }

        // 해당 유저가 같은 쿠폰을 등록했는지 체크하기
        if(couponUseRepository.existsByCouponAndUser(coupon, user)){
            log.warn("{}", Message.COUPON_DUPLICATED);
            throw new CouponDuplicatedException();
        }

        coupon.useCoupon(); // 쿠폰 사용 (count - 1)

        // 그렇지 않을 경우 쿠폰 등록해주기 (userOrderDetail은 아직 사용하기 전이기 때문에 null로 남겨둠.)
        CouponUse couponUse = CouponUse.builder()
                .coupon(coupon)
                .user(user)
                .couponUse(false) // 아직 사용하지 않은상태
                .build();

        couponUseRepository.save(couponUse); // 쿠폰 등록
        return new ResponseDTO<>(HttpStatus.OK.value(), "쿠폰이 등록되었습니다.");
    }

    public ResponseDTO<?> selectUserCoupon(String userId){
        User user = userRepository.findById(userId).orElseThrow(() -> {
            log.warn("{}", Message.USER_NOT_FOUND);
            throw new UserNotFoundException();
        });

        List<CouponUse> couponUses = couponUseRepository.findAllByUserAndCouponUseFalse(user);
        couponUses.removeIf(cu -> cu.getCoupon().couponExpireCheck()); // 기한이 지났으면 삭제

        // 사용하지 않은 쿠폰목록 조회
        return new ResponseDTO<>(HttpStatus.OK.value(), couponUses);
    }


}
