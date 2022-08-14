package com.proceed.swhackathon.controller;

import com.proceed.swhackathon.dto.ResponseDTO;
import com.proceed.swhackathon.repository.DestinationRepository;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/destination")
@RequiredArgsConstructor
public class DestinationController {

    private final DestinationRepository destinationRepository;

    @ApiOperation(value = "모든 목적지정보 출력", notes = "")
    @GetMapping("/all")
    public ResponseDTO<?> seletAll(){
        return new ResponseDTO<>(HttpStatus.OK.value(),
                destinationRepository.findAll());
    }
}
