package com.proceed.swhackathon.service;

import com.proceed.swhackathon.dto.orderDetail.OrderDetailDTO;
import com.proceed.swhackathon.dto.orderDetail.OrderDetailInsertDTO;
import com.proceed.swhackathon.exception.menu.MenuNotFoundException;
import com.proceed.swhackathon.exception.order.OrderNotFoundException;
import com.proceed.swhackathon.exception.user.UserNotFoundException;
import com.proceed.swhackathon.model.Menu;
import com.proceed.swhackathon.model.Order;
import com.proceed.swhackathon.model.OrderDetail;
import com.proceed.swhackathon.model.User;
import com.proceed.swhackathon.repository.MenuRepository;
import com.proceed.swhackathon.repository.OrderDetailRepository;
import com.proceed.swhackathon.repository.OrderRepository;
import com.proceed.swhackathon.repository.UserRepository;
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

    @Transactional
    public OrderDetailDTO insertOrderDetail(String userId,
                                            Long orderId,
                                            Long menuId,
                                            OrderDetailInsertDTO orderDetailDTO){
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
        orderDetail.calTotalPrice();

        return OrderDetailDTO.entityToDTO(orderDetailRepository.save(orderDetail));
    }

    public List<OrderDetailDTO> selectCart(String userId, Long orderId){
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
}
