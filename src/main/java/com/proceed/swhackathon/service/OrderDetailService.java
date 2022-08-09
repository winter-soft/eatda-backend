package com.proceed.swhackathon.service;

import com.proceed.swhackathon.dto.orderDetail.OrderDetailDTO;
import com.proceed.swhackathon.dto.orderDetail.OrderDetailInsertDTO;
import com.proceed.swhackathon.dto.userOrderDetail.UserOrderDetailDTO;
import com.proceed.swhackathon.exception.menu.MenuNotFoundException;
import com.proceed.swhackathon.exception.order.OrderNotFoundException;
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
    public void updateMenuCheck(String userId, Long orderId, Long menuId){
        User user = userRepository.findById(userId).orElseThrow(() -> {
            throw new UserNotFoundException();
        });
        Order order = orderRepository.findById(orderId).orElseThrow(() -> {
            throw new OrderNotFoundException();
        });
        Menu menu = menuRepository.findById(menuId).orElseThrow(() -> {
            throw new MenuNotFoundException();
        });

        orderDetailRepository.findByUserAndOrderAndMenu(user, order, menu)
                .stream()
                .forEach(od -> {
                    od.triggerCheck();
                });
        return ;

    }

    @Transactional
    public Long addOrder(String userId, Long orderId) {
        User user = userRepository.findById(userId).orElseThrow(() -> {
            throw new UserNotFoundException();
        });
        Order order = orderRepository.findById(orderId).orElseThrow(() -> {
            throw new OrderNotFoundException();
        });

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
        Order order = orderRepository.findById(orderId).orElseThrow(() -> {
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

        return UserOrderDetailDTO.entityToDTO(uod);
    }
}
