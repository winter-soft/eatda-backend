//package com.proceed.swhackathon.repository;
//
//import com.proceed.swhackathon.config.security.jwt.TokenProvider;
//import com.proceed.swhackathon.dto.menu.MenuNameDTO;
//import com.proceed.swhackathon.dto.review.ReviewResponseDTO;
//import com.proceed.swhackathon.model.*;
//import com.proceed.swhackathon.utils.DateDistance;
//import com.querydsl.core.Tuple;
//import com.querydsl.core.group.GroupBy;
//import com.querydsl.core.types.Projections;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import io.jsonwebtoken.Claims;
//import org.aspectj.lang.annotation.Before;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Slice;
//import org.springframework.security.authentication.AbstractAuthenticationToken;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.core.authority.AuthorityUtils;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.annotation.PostConstruct;
//import javax.persistence.EntityManager;
//import javax.servlet.http.HttpServletRequest;
//
//import java.util.Date;
//import java.util.List;
//
//import static com.proceed.swhackathon.model.QMenu.menu;
//import static com.proceed.swhackathon.model.QOrder.order;
//import static com.proceed.swhackathon.model.QOrderDetail.orderDetail;
//import static com.proceed.swhackathon.model.QReview.*;
//import static com.proceed.swhackathon.model.QReview.review;
//import static com.proceed.swhackathon.model.QUser.user;
//import static com.proceed.swhackathon.model.QUserOrderDetail.userOrderDetail;
//import static com.querydsl.core.group.GroupBy.groupBy;
//import static com.querydsl.core.group.GroupBy.list;
//import static com.querydsl.core.types.Projections.list;
//import static org.assertj.core.api.Assertions.*;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@Transactional
//@Rollback(value = false)
//class ReviewRepositoryTest {
//
//    @Autowired UserRepository userRepository;
//    @Autowired ReviewRepository reviewRepository;
//    @Autowired UserOrderDetailRepository userOrderDetailRepository;
//    @Autowired StoreRepository storeRepository;
//    @Autowired OrderRepository orderRepository;
//    @Autowired OrderDetailRepository orderDetailRepository;
//    @Autowired MenuRepository menuRepository;
//
//    @Autowired EntityManager em;
//
//    JPAQueryFactory queryFactory;
//
////    @BeforeEach
////    public void before() {
////        queryFactory = new JPAQueryFactory(em);
////
////        Menu menu1 = Menu.builder()
////                .name("마라탕")
////                .build();
////
////        Menu menu2 = Menu.builder()
////                .name("짜장면")
////                .build();
////
////        Menu menu3 = Menu.builder()
////                .name("탕수육")
////                .build();
////
////        Menu saveMenu1 = menuRepository.save(menu1);
////        Menu saveMenu2 = menuRepository.save(menu2);
////        Menu saveMenu3 = menuRepository.save(menu3);
////
////
////        User user1 = User.builder()
////                .username("ghdcksgml")
////                .platformId("13323233")
////                .email("fhdk@nadfasfsaver.com")
////                .role(Role.USER)
////                .build();
////
////        User user2 = User.builder()
////                .username("ghdcksgml2")
////                .platformId("132323333")
////                .email("fhdk@naver.cfsfsfom")
////                .role(Role.USER)
////                .build();
////
////        User user3 = User.builder()
////                .username("ghdcksgml3")
////                .platformId("132344433")
////                .email("fhdk@naver.csfafsom")
////                .role(Role.USER)
////                .build();
////
////        User save1 = userRepository.save(user1);
////        User save2 = userRepository.save(user2);
////        User save3 = userRepository.save(user3);
////
////        Store store = Store.builder()
////                .name("aaa")
////                .minOrderPrice(1000)
////                .build();
////
////        Store storeA = Store.builder()
////                .name("aaa")
////                .minOrderPrice(1000)
////                .build();
////
////        storeRepository.save(store);
////        storeRepository.save(storeA);
////
////        Order order = Order.builder()
////                .store(store)
////                .build();
////
////        Order orderA = Order.builder()
////                .store(storeA)
////                .build();
////
////        orderRepository.save(order);
////        orderRepository.save(orderA);
////
////        UserOrderDetail uod1 = UserOrderDetail.builder()
////                .user(user1)
////                .order(order)
////                .orderDetails(null)
////                .userOrderDetailStatus("DONE")
////                .totalPrice(0)
////                .build();
////        userOrderDetailRepository.save(uod1);
////
////        UserOrderDetail uod2 = UserOrderDetail.builder()
////                .user(user2)
////                .order(order)
////                .orderDetails(null)
////                .userOrderDetailStatus("DONE")
////                .totalPrice(0)
////                .build();
////        userOrderDetailRepository.save(uod2);
////
////        UserOrderDetail uod3 = UserOrderDetail.builder()
////                .user(user3)
////                .order(orderA)
////                .orderDetails(null)
////                .userOrderDetailStatus("DONE")
////                .totalPrice(0)
////                .build();
////        userOrderDetailRepository.save(uod3);
////
////        OrderDetail od1 = OrderDetail.builder()
////                .menu(saveMenu1)
////                .userOrderDetail(uod1)
////                .build();
////
////        OrderDetail od2 = OrderDetail.builder()
////                .menu(saveMenu2)
////                .userOrderDetail(uod2)
////                .build();
////
////        OrderDetail od3 = OrderDetail.builder()
////                .menu(saveMenu3)
////                .userOrderDetail(uod3)
////                .build();
////        OrderDetail od4 = OrderDetail.builder()
////                .menu(saveMenu3)
////                .userOrderDetail(uod1)
////                .build();
////
////        orderDetailRepository.save(od1);
////        orderDetailRepository.save(od3);
////        orderDetailRepository.save(od2);
////        orderDetailRepository.save(od4);
////
////        Review review1 = Review.builder()
////                .imageUrl(null)
////                .star(5)
////                .content("맛있네요")
////                .createdBy(save1.getId())
////                .userOrderDetail(uod1)
////                .visible(true)
////                .build();
////
////        Review review2 = Review.builder()
////                .imageUrl(null)
////                .star(3)
////                .createdBy(save2.getId())
////                .content("음식이 친절하고 사장님이 맛있어요.")
////                .userOrderDetail(uod2)
////                .visible(true)
////                .build();
////        reviewRepository.save(review1);
////        reviewRepository.save(review2);
////
////        Review review3 = Review.builder()
////                .imageUrl(null)
////                .star(3)
////                .createdBy(save3.getId())
////                .content("맛있네요")
////                .userOrderDetail(uod3)
////                .visible(true)
////                .build();
////        reviewRepository.save(review3);
////
////        em.flush();
////        em.clear();
////
////    }
//
////    @Test
////    @DisplayName("단건 조회")
////    public void findById() throws Exception{
////        // given
////        Long reviewId = 5L;
////
////        // when
////        List<ReviewResponseDTO> transform = queryFactory
////                .from(review)
////                .join(review.userOrderDetail, userOrderDetail)
////                .join(userOrderDetail.orderDetails, orderDetail)
////                .join(orderDetail.menu, menu)
////                .join(user).on(review.createdBy.eq(user.id))
////                .where(review.id.eq(reviewId))
////                .transform(
////                        groupBy(review.id).list(
////                                Projections.fields(
////                                        ReviewResponseDTO.class,
////                                        review.id,
////                                        review.imageUrl,
////                                        review.star,
////                                        review.content,
////                                        user.username.as("createdBy"),
////                                        list(
////                                                Projections.fields(
////                                                        MenuNameDTO.class,
////                                                        menu.id,
////                                                        menu.name
////                                                )
////                                        ).as("menuName"),
////                                        review.createdTime.as("orderDate"),
////                                        review.visible
////                                )
////                        )
////                );
////
////        //then
////        assertThat(transform.size()).isEqualTo(0);
////    }
//
////    @Test
////    @DisplayName("reviewId와 userId를 통해 리뷰 작성자인지 검증")
////    public void validReviewWriter() throws Exception{
////        // given
////        Long reviewId = 8L;
////        String userId = "40285be785c07bb00185c07bbaed0001";
////
////        // when
////        Review result = queryFactory
////                .selectFrom(review)
////                .where(
////                        review.id.eq(reviewId)
////                                .and(review.createdBy.eq(userId))
////                                .and(review.visible.eq(true)))
////                .fetchOne();
////
////        //then
////        assertThat(result.getId()).isEqualTo(8L);
////        assertThat(result.isVisible()).isTrue();
////    }
//
////    @Test
////    public void joinTest() throws Exception{
////        // given
////
////        // when
////        List<Tuple> results = queryFactory
////                .select(order.store.id,
////                        review.star.avg())
////                .from(review)
////                .join(review.userOrderDetail, userOrderDetail)
////                .join(userOrderDetail.order, order)
////                .groupBy(order.store)
////                .fetch();
////
////        for(Tuple reuslt : results) {
////            System.out.println("reuslt.get(0) = " + reuslt.get(0,Long.class));
////            System.out.println("reuslt.get(1) = " + reuslt.get(1, Double.class));
////        }
////
////        //then
////    }
//
////    @Test
////    public void findByStoreId_RelativeDateColumn_NullableTest() throws Exception{
////        // given
////        PageRequest pageRequest = PageRequest.of(0, 5);
////
////        // when
////        List<ReviewResponseDTO> reviewAll = queryFactory
////                .from(review)
////                .join(review.userOrderDetail, userOrderDetail)
////                .join(userOrderDetail.user, user)
////                .join(userOrderDetail.orderDetails, orderDetail)
////                .join(orderDetail.menu, menu)
////                .join(userOrderDetail.order, order)
////                .where(order.store.id.eq(1L)
////                        .and(review.visible.eq(false)))
////                .offset(pageRequest.getOffset())
////                .limit(pageRequest.getPageSize())
////                .orderBy(review.id.desc())
////                .transform(
////                        groupBy(review.id).list(
////                                Projections.fields(
////                                        ReviewResponseDTO.class,
////                                        review.id,
////                                        review.imageUrl,
////                                        review.star,
////                                        review.content,
////                                        user.username.as("createdBy"),
////                                        list(
////                                                Projections.fields(
////                                                        MenuNameDTO.class,
////                                                        menu.id,
////                                                        menu.name
////                                                )
////                                        ).as("menuName"),
////                                        review.createdTime.as("orderDate"),
////                                        review.visible
////                                )
////                        )
////                );
////
////        //then
////        assertThat(reviewAll.get(0).getRelativeDate()).isNull();
////    }
////
////    @Test
////    @DisplayName("findByStoreId 테스트")
////    public void reviewFindAllTest() throws Exception{
////        // given
////        PageRequest pageRequest = PageRequest.of(0, 5);
////
////        // when
////        List<ReviewResponseDTO> reviewAll = queryFactory
////                .from(review)
////                .join(review.userOrderDetail, userOrderDetail)
////                .join(userOrderDetail.user, user)
////                .join(userOrderDetail.orderDetails, orderDetail)
////                .join(orderDetail.menu, menu)
////                .join(userOrderDetail.order, order)
////                .where(order.store.id.eq(3L)
////                        .and(review.visible.eq(false)))
////                .offset(pageRequest.getOffset())
////                .limit(pageRequest.getPageSize())
////                .orderBy(review.id.desc())
////                .transform(
////                        groupBy(review.id).list(
////                                Projections.fields(
////                                        ReviewResponseDTO.class,
////                                        review.id,
////                                        review.imageUrl,
////                                        review.star,
////                                        review.content,
////                                        user.username.as("createdBy"),
////                                        list(
////                                                Projections.fields(
////                                                        MenuNameDTO.class,
////                                                        menu.id,
////                                                        menu.name
////                                                )
////                                        ).as("menuName"),
////                                        review.createdTime.as("orderDate"),
////                                        review.visible
////                                )
////                        )
////                );
////
////        //then
////        assertThat(reviewAll.size()).isEqualTo(0);
////    }
//
////    @Test
////    @DisplayName(value = "리뷰생성 테스트")
////    public void createdByTest() throws Exception {
////        // given
////
////        // when
////        Review findReview = reviewRepository.findById(1L).get();
////
////        //then
////        assertThat(findReview.getId()).isEqualTo(1L);
////    }
////
////    @Test
////    @DisplayName(value = "joinFetch 테스트")
////    public void joinFetchTest() throws Exception{
////        // given
////        PageRequest pageRequest = PageRequest.of(0, 3);
////
////        // when
////        Slice<Review> result = reviewRepository.findSliceReviewAll(pageRequest);
////        List<Review> contents = result.getContent();
////
////        //then
////        for(Review r : contents) {
////            System.out.println("r = " + r.getContent());
////        }
////    }
//}