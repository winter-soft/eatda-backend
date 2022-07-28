package com.proceed.swhackathon.repository;

import com.proceed.swhackathon.model.OrderDetail;
import com.proceed.swhackathon.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

    @Query("select od from OrderDetail od where od.menu.store = :store")
    public List<OrderDetail> findByStore(Store store);
}
