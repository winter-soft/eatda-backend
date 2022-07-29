package com.proceed.swhackathon.controller;

import com.proceed.swhackathon.dto.MenuDTO;
import com.proceed.swhackathon.dto.ResponseDTO;
import com.proceed.swhackathon.dto.StoreDTO;
import com.proceed.swhackathon.dto.StoreDetailDTO;
import com.proceed.swhackathon.exception.IllegalArgumentException;
import com.proceed.swhackathon.exception.store.StoreNotFoundException;
import com.proceed.swhackathon.model.Menu;
import com.proceed.swhackathon.model.Store;
import com.proceed.swhackathon.repository.StoreRepository;
import com.proceed.swhackathon.service.StoreService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/store")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;
    private final StoreRepository storeRepository;

    @ApiOperation(value = "가게상세", notes = "주문번호를 받아,  가게상세 페이지로 가게정보, 해당오더정보, 메뉴, 좋아요수를 리턴합니다.")
    @GetMapping("/storeDetail/{orderId}")
    public ResponseDTO<?> storeDetail(@PathVariable Long orderId){
        storeService.initialize();
        StoreDetailDTO storeDetailDTO = storeService.storeDetail(orderId);

        return new ResponseDTO<>(HttpStatus.OK.value(), storeDetailDTO);
    }

    @ApiOperation(value = "가게등록", notes = "매장명, 최소주문금액, 배경 이미지를 넣어 가게 정보 등록")
    @PostMapping("/insert")
    public ResponseDTO<?> insert(StoreDTO storeDTO){
        return new ResponseDTO<>(HttpStatus.OK.value(), storeService.insert(storeDTO));
    }

    @ApiOperation(value = "가게 한건 조회", notes = "{id}를 통해 가게를 조회한다.")
    @GetMapping("/{id}")
    public ResponseDTO<?> select(@PathVariable Long id){
        return new ResponseDTO<>(HttpStatus.OK.value(), storeRepository.findById(id).orElseThrow(()->{
           throw new StoreNotFoundException();
        }));
    }

    @ApiOperation(value = "가게 여러건 조회", notes = "pageNumber=0&pageSize=10")
    @GetMapping("/")
    public ResponseDTO<?> selectAll(@PageableDefault(sort = "id", direction = Sort.Direction.DESC)
                                                Pageable pageable){
        return new ResponseDTO<>(HttpStatus.OK.value(), storeService.selectAll(pageable));
    }

    @ApiOperation(value = "가게정보 수정", notes = "id, name, minOrderPrice, backgroundImageUrl, category, infor을 받는다.")
    @PostMapping("/update")
    public ResponseDTO<?> update(@RequestBody StoreDTO storeDTO){
        return new ResponseDTO<>(HttpStatus.OK.value(), storeService.update(storeDTO));
    }

    @ApiOperation(value = "가게정보 삭제", notes = "storeId를 받아서 가게정보를 삭제한다.")
    @GetMapping("/delete/{storeId}")
    public ResponseDTO<?> delete(@PathVariable Long storeId){
        return new ResponseDTO<>(HttpStatus.OK.value(), storeService.delete(storeId)+"이(가) 삭제 되었습니다.");
    }
}
