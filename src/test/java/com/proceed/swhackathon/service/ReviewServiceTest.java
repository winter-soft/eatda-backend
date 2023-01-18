//package com.proceed.swhackathon.service;
//
//import com.proceed.swhackathon.model.Order;
//import com.proceed.swhackathon.model.Review;
//import com.proceed.swhackathon.model.Store;
//import com.proceed.swhackathon.model.UserOrderDetail;
//import com.proceed.swhackathon.repository.*;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.persistence.EntityManager;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.*;
//
//@Transactional
//@SpringBootTest
//class ReviewServiceTest {
//    @Autowired private ReviewRepository reviewRepository;
//    @Autowired UserOrderDetailRepository userOrderDetailRepository;
//    @Autowired StoreRepository storeRepository;
//    @Autowired OrderRepository orderRepository;
//
//    @Autowired private ReviewService reviewService;
//    @Autowired EntityManager em;
//
//    @BeforeEach
//    public void before() {
//
//        Store store = Store.builder()
//                .name("aaa")
//                .minOrderPrice(1000)
//                .build();
//
//        Store storeA = Store.builder()
//                .name("aaa")
//                .minOrderPrice(1000)
//                .build();
//
//        storeRepository.save(store);
//        storeRepository.save(storeA);
//
//        Order order = Order.builder()
//                .store(store)
//                .build();
//
//        Order orderA = Order.builder()
//                .store(storeA)
//                .build();
//
//        orderRepository.save(order);
//        orderRepository.save(orderA);
//
//        UserOrderDetail uod1 = UserOrderDetail.builder()
//                .user(null)
//                .order(order)
//                .orderDetails(null)
//                .userOrderDetailStatus("DONE")
//                .totalPrice(0)
//                .build();
//        userOrderDetailRepository.save(uod1);
//
//        UserOrderDetail uod2 = UserOrderDetail.builder()
//                .user(null)
//                .order(order)
//                .orderDetails(null)
//                .userOrderDetailStatus("DONE")
//                .totalPrice(0)
//                .build();
//        userOrderDetailRepository.save(uod2);
//
//        UserOrderDetail uod3 = UserOrderDetail.builder()
//                .user(null)
//                .order(orderA)
//                .orderDetails(null)
//                .userOrderDetailStatus("DONE")
//                .totalPrice(0)
//                .build();
//        userOrderDetailRepository.save(uod3);
//
//        Review review1 = Review.builder()
//                .imageUrl(null)
//                .star(5)
//                .content("맛있네요")
//                .userOrderDetail(uod1)
//                .build();
//
//        Review review2 = Review.builder()
//                .imageUrl(null)
//                .star(3)
//                .content("음식이 친절하고 사장님이 맛있어요.")
//                .userOrderDetail(uod2)
//                .build();
//        reviewRepository.save(review1);
//        reviewRepository.save(review2);
//
//        Review review3 = Review.builder()
//                .imageUrl(null)
//                .star(3)
//                .content("맛있네요")
//                .userOrderDetail(uod3)
//                .build();
//        reviewRepository.save(review3);
//
//        em.flush();
//        em.clear();
//
//    }
//
//    @Test
//    @DisplayName(value = "ServiceTest")
//    public void dirtyCheckTest() throws Exception{
//        // given
//
//        // when
//        reviewService.reviewDslTest();
//        em.flush();
//        em.clear();
//
//        Review result = reviewRepository.findById(1L).get();
//
//        //then
//        assertThat(result.getContent()).isEqualTo("ancd");
//    }
//
//}