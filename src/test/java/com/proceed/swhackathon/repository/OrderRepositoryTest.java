package com.proceed.swhackathon.repository;

import com.proceed.swhackathon.dto.order.OrderDTO;
import com.proceed.swhackathon.model.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//@SpringBootTest
//@Transactional(readOnly = true)
//class OrderRepositoryTest {
//
//    @Autowired OrderRepository orderRepository;
//
//    @Test
//    void findTestById() {
//        Order testById = orderRepository.findTestById(20L);
//        OrderDTO orderDTO = OrderDTO.entityToDTO(testById, null);
//        System.out.println("testById = " + orderDTO);
//    }
//}