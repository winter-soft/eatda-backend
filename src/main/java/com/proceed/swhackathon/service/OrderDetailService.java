package com.proceed.swhackathon.service;

import com.proceed.swhackathon.dto.orderDetail.OrderDetailDTO;
import com.proceed.swhackathon.dto.orderDetail.OrderDetailInsertDTO;
import com.proceed.swhackathon.dto.userOrderDetail.UserOrderDetailDTO;
import com.proceed.swhackathon.exception.menu.MenuNotFoundException;
import com.proceed.swhackathon.exception.order.OrderNotFoundException;
import com.proceed.swhackathon.exception.order.OrderStatusException;
import com.proceed.swhackathon.exception.user.UserNotFoundException;
import com.proceed.swhackathon.exception.userOrderDetail.UserOrderDetailNotFoundException;
import com.proceed.swhackathon.model.*;
import com.proceed.swhackathon.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderDetailService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final MenuRepository menuRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final UserOrderDetailRepository userOrderDetailRepository;

    @Transactional
    public OrderDetailDTO insertOrderDetail(String userId,
                                            Long orderId,
                                            Long menuId,
                                            OrderDetailInsertDTO orderDetailDTO) {
        User user = userRepository.findById(userId).orElseThrow(() -> {
            throw new UserNotFoundException();
        });
        Order order = orderRepository.findById(orderId).orElseThrow(() -> {
            throw new OrderNotFoundException();
        });
        Menu menu = menuRepository.findById(menuId).orElseThrow(() -> {
            throw new MenuNotFoundException();
        });

        OrderDetail orderDetail = OrderDetail.builder()
                .quantity(orderDetailDTO.getQuantity())
                .menuCheck(true)
                .build();
        orderDetail.setUser(user);
        orderDetail.setMenu(menu);
        orderDetail.setOrder(order);
        orderDetail.calTotalPrice();

        return OrderDetailDTO.entityToDTO(orderDetailRepository.save(orderDetail));
    }

    public List<OrderDetailDTO> selectCart(String userId, Long orderId) {
        User user = userRepository.findById(userId).orElseThrow(() -> {
            throw new UserNotFoundException();
        });
        Order order = orderRepository.findById(orderId).orElseThrow(() -> {
            throw new OrderNotFoundException();
        });

        return orderDetailRepository.findByUserAndOrder(user, order)
                .stream()
                .map(OrderDetailDTO::entityToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public String updateMenuCheck(String userId, Long orderId, Long menuId){
        User user = userRepository.findById(userId).orElseThrow(() -> {
            throw new UserNotFoundException();
        });
        Order order = orderRepository.findById(orderId).orElseThrow(() -> {
            throw new OrderNotFoundException();
        });
        Menu menu = menuRepository.findById(menuId).orElseThrow(() -> {
            throw new MenuNotFoundException();
        });

        OrderDetail ods = orderDetailRepository.findByUserAndOrderAndMenu(user, order, menu).orElseThrow(() -> {
            throw new UserOrderDetailNotFoundException();
        });

        if(ods == null) return "메뉴를 찾지 못했습니다.";
        else
            return ods.triggerCheck() ? "메뉴를 체크했습니다." : "메뉴를 체크해제했습니다.";
    }

    @Transactional
    public Long addOrder(String userId, Long orderId) {
        User user = userRepository.findById(userId).orElseThrow(() -> {
            throw new UserNotFoundException();
        });
        Order order = orderRepository.findById(orderId).orElseThrow(() -> {
            throw new OrderNotFoundException();
        });

        if(order.getOrderStatus() != OrderStatus.WAITING){
            throw new OrderStatusException();
        }

        UserOrderDetail result = userOrderDetailRepository.findByOrderAndUserWithOrder(order, user).orElse(null);
        if(result != null){
            result.cancel();
            userOrderDetailRepository.delete(result);
        }

        UserOrderDetail uod = UserOrderDetail.builder()
                .order(order)
                .user(user)
                .build();

        return userOrderDetailRepository.save(uod).getId();
    }

    @Transactional
    public UserOrderDetailDTO detachUOD(String userId, Long orderId, Long uodId){
        User user = userRepository.findById(userId).orElseThrow(() -> {
            throw new UserNotFoundException();
        });
        Order order = orderRepository.findOrderByIdWithStore(orderId).orElseThrow(() -> {
            throw new OrderNotFoundException();
        });
        UserOrderDetail uod = userOrderDetailRepository.findById(uodId).orElseThrow(() -> {
            throw new UserOrderDetailNotFoundException();
        });

        List<OrderDetail> ods = orderDetailRepository.findByUserAndOrder(user, order);
        for (OrderDetail od : ods) {
            od.setUserOrderDetail(uod);
        }

        uod.setOrderDetails(ods);
        uod.calTotalPrice();

        // 최근 주문 오더 수정
        order.getStore().setRecentlyOrder(order);

        return UserOrderDetailDTO.entityToDTO(uod);
    }

    public UserOrderDetailDTO selectUOD(String userId, Long orderId){
        User user = userRepository.findById(userId).orElseThrow(() -> {
            throw new UserNotFoundException();
        });
        Order order = orderRepository.findById(orderId).orElseThrow(() -> {
            throw new OrderNotFoundException();
        });

        UserOrderDetail uod = userOrderDetailRepository.findByOrderAndUser(order, user).orElseThrow(() -> {
            throw new UserOrderDetailNotFoundException();
        });

        List<OrderDetail> ods = orderDetailRepository.selectUOD(uod, user);
        ods.removeIf(od -> !od.isMenuCheck());
        uod.setOrderDetails(ods);

        return UserOrderDetailDTO.entityToDTO(uod);
    }
}
