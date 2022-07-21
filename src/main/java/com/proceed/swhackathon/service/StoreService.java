package com.proceed.swhackathon.service;

import com.proceed.swhackathon.dto.MenuDTO;
import com.proceed.swhackathon.dto.OrderDTO;
import com.proceed.swhackathon.dto.StoreDTO;
import com.proceed.swhackathon.exception.order.OrderNotFoundException;
import com.proceed.swhackathon.exception.store.StoreNotFoundException;
import com.proceed.swhackathon.model.*;
import com.proceed.swhackathon.repository.DestinationRepository;
import com.proceed.swhackathon.repository.OrderRepository;
import com.proceed.swhackathon.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreService {

    private final DestinationRepository destinationRepository;
    private final OrderRepository orderRepository;
    private final StoreRepository storeRepository;

    // dummy data
    @Transactional
    public void initialize(){
        Destination d1 = Destination.builder()
                .name("단국대학교 기숙사 진리관 로비")
                .build();
        Store s1 = Store.builder()
                .name("단대앞분식")
                .minOrderPrice(12000)
                .backgroundImageUrl("https://naver.com/")
                .build();
        Menu m1 = Menu.builder()
                .name("엄마 손맛 떡볶이")
                .price(3500)
                .store(s1)
                .imageUrl("https://daum.net/")
                .build();
        Menu m2 = Menu.builder()
                .name("오징어 튀김")
                .price(3500)
                .store(s1)
                .imageUrl("https://daum.net/")
                .build();
        Menu m3 = Menu.builder()
                .name("잔치국수")
                .price(3500)
                .store(s1)
                .imageUrl("https://daum.net/")
                .build();

        Order o1 = Order.builder()
                .orderStatus(OrderStatus.WAITING)
                .currentAmount(30000)
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now().plusHours(1))
                .store(s1)
                .destination(d1)
                .build();


        s1.addMenu(m1);
        s1.addMenu(m2);
        s1.addMenu(m3);

        destinationRepository.save(d1);
        storeRepository.save(s1);
        orderRepository.save(o1);
    }

    public OrderDTO storeDetail(Long id){
        Order order = orderRepository.findOrderByIdWithStore(id).orElseThrow(() -> {
            throw new OrderNotFoundException();
        });
        Store store = storeRepository.findByIdWithMenus(order.getStore().getId()).orElseThrow(()->{
            throw new StoreNotFoundException();
        });

        List<MenuDTO> menuDTOList = new ArrayList<>();
        for(Menu m : store.getMenus()){
            MenuDTO menuDTO = MenuDTO.builder()
                    .id(m.getId())
                    .store(m.getStore())
                    .name(m.getName())
                    .imageUrl(m.getImageUrl())
                    .price(m.getPrice())
                    .build();
            menuDTOList.add(menuDTO);
        }

        StoreDTO storeDTO = StoreDTO.builder()
                .id(store.getId())
                .name(store.getName())
                .minOrderPrice(store.getMinOrderPrice())
                .backgroundImageUrl(store.getBackgroundImageUrl())
                .menus(menuDTOList)
                .likes(store.getLikesCount())
                .build();

        OrderDTO orderDTO = OrderDTO.builder()
                .id(order.getId())
                .destination(order.getDestination())
                .orderStatus(order.getOrderStatus())
                .currentAmount(order.getCurrentAmount())
                .startTime(order.getStartTime())
                .endTime(order.getEndTime())
                .store(storeDTO)
                .build();

        return orderDTO;
    }

}
