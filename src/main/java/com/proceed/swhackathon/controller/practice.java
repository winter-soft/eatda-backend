package com.proceed.swhackathon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class practice {
    @GetMapping("/naver")
    public String index() {
        return "naver";
    }
}
