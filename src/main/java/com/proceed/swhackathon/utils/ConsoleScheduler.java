package com.proceed.swhackathon.utils;

import com.proceed.swhackathon.service.OrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class ConsoleScheduler { // 이 컴포넌트는 crontab을 위해 제작되었습니다.

    private final OrderDetailService orderDetailService;

    @Scheduled(cron = "0 0 1 * * *")
    public void printDate() {
        System.out.println("LocalDateTime.now() = " + LocalDateTime.now());
        orderDetailService.garbageCollectOrderDetail();
    }
}
