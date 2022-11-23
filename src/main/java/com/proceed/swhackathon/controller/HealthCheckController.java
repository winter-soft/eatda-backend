package com.proceed.swhackathon.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @GetMapping("/")
    public String healthCheck(){
        return "The service is up and running...";
    }

    @GetMapping("/test")
    public String healthTest() {
        return healthCheck();
    }
}
