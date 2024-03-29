package com.proceed.swhackathon.repository;

import com.proceed.swhackathon.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, String> {

    @Query("select p from Payment p where p.userOrderDetail_id = :useOrderDetail_id")
    Optional<Payment> findByUserOrderDetail_id(@Param("useOrderDetail_id")Long useOrderDetail_id);
}
