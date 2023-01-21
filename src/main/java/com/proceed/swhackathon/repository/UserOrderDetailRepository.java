package com.proceed.swhackathon.repository;

import com.proceed.swhackathon.model.Order;
import com.proceed.swhackathon.model.User;
import com.proceed.swhackathon.model.UserOrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserOrderDetailRepository extends JpaRepository<UserOrderDetail, Long> {

    @Query("select uod from UserOrderDetail uod where uod.order = :order")
    List<UserOrderDetail> findByOrderAll(@Param("order")Order order);

    @Query("select uod from UserOrderDetail uod where uod.order = :order and uod.user = :user")
    Optional<List<UserOrderDetail>> findAllByOrderAndUser(@Param("order")Order order, @Param("user")User user);

    @Query("select uod from UserOrderDetail uod where uod.order = :order")
    List<UserOrderDetail> findAllByOrderWithOrderDetail(@Param("order")Order order);

    @Query("select uod from UserOrderDetail uod join fetch uod.order o where uod.id = :userOrderDetail_id")
    Optional<UserOrderDetail> findByIdWithOrder(@Param("userOrderDetail_id")Long userOrderDetail_id);

    @Query("select uod from UserOrderDetail uod join fetch uod.order o where uod.user = :user ORDER BY uod.id DESC")
    List<UserOrderDetail> findAllByUserWithOrderOrderByIdDesc(@Param("user")User user);

    @Query("select uod.user from UserOrderDetail uod where uod.order = :order")
    List<User> findAllByOrder(@Param("order")Order order);

    @Query("select uod from UserOrderDetail uod join fetch uod.user u where uod.id = :uodId and u.id = :userId")
    Optional<UserOrderDetail> findUODByIdAndUser(@Param("uodId")Long uodId, @Param("userId")String userId);
}
