package com.proceed.swhackathon.service;

import com.proceed.swhackathon.exception.store.StoreNotFoundException;
import com.proceed.swhackathon.model.Menu;
import com.proceed.swhackathon.model.Store;
import com.proceed.swhackathon.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreService {

    private final StoreRepository storeRepository;

    // dummy data
    @Transactional
    public void initialize(){
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

        s1.addMenu(m1);
        s1.addMenu(m2);
        s1.addMenu(m3);

        storeRepository.save(s1);
    }

    public Store storeDetail(Long id){
        Store store = storeRepository.findByIdWithMenus(id).orElseThrow(()->{
            throw new StoreNotFoundException();
        });
        return store;
    }

}
