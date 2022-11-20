package com.proceed.swhackathon.repository;

import com.proceed.swhackathon.model.OrderDetailOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailOptionRepository extends JpaRepository<OrderDetailOption, Long> {
}
