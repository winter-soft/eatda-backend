package com.proceed.swhackathon.repository;

import com.proceed.swhackathon.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, String> {
}
