package com.proceed.swhackathon.service;

import com.proceed.swhackathon.dto.OrderDTO;
import com.proceed.swhackathon.exception.store.StoreNotFoundException;
import com.proceed.swhackathon.model.Order;
import com.proceed.swhackathon.model.Store;
import com.proceed.swhackathon.repository.OrderRepository;
import com.proceed.swhackathon.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepository orderRepository;
    private final StoreRepository storeRepository;

    @Transactional
    public OrderDTO insert(OrderDTO orderDTO, Long storeId){
        Store store = storeRepository.findById(storeId).orElseThrow(() -> {
            throw new StoreNotFoundException();
        });
        orderDTO.setStore(store);
        return OrderDTO.entityToDTO(orderRepository.save(orderDTO.dtoToEntity()));
    }
}
