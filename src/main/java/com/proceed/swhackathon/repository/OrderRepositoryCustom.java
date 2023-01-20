package com.proceed.swhackathon.repository;

import com.proceed.swhackathon.dto.order.OrderDTO;

import java.util.List;

public interface OrderRepositoryCustom {
    public List<OrderDTO> findOrder();
}
