package com.proceed.swhackathon.repository;

import com.proceed.swhackathon.dto.order.OrderDTO;
import com.proceed.swhackathon.model.Order;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional(readOnly = true)
class OrderRepositoryTest {

    @Autowired OrderRepository orderRepository;
    @Autowired EntityManager em;
    JPAQueryFactory queryFactory;

    @BeforeEach
    public void before() {
        queryFactory = new JPAQueryFactory(em);

    }

    @Test
    public void findOrder() throws Exception{
        // given

        // when
        List<OrderDTO> dtos = orderRepository.findOrder();

        //then
        for(OrderDTO dto : dtos) {
            System.out.println("dto = " + dto);
        }
    }
}