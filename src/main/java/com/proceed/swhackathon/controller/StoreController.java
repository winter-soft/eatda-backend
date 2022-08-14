package com.proceed.swhackathon.controller;

import com.proceed.swhackathon.dto.ResponseDTO;
import com.proceed.swhackathon.dto.store.CategoryDTO;
import com.proceed.swhackathon.dto.store.StoreDetailDTO;
import com.proceed.swhackathon.dto.store.StoreInsertDTO;
import com.proceed.swhackathon.exception.store.StoreNotFoundException;
import com.proceed.swhackathon.model.Category;
import com.proceed.swhackathon.repository.StoreRepository;
import com.proceed.swhackathon.service.S3Service;
import com.proceed.swhackathon.service.StoreService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/store")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;
    private final StoreRepository storeRepository;
    private final S3Service s3Service;

    @ApiOperation(value = "가게상세", notes = "주문번호를 받아,  가게상세 페이지로 가게정보, 해당오더정보, 메뉴, 좋아요수를 리턴합니다.")
    @GetMapping("/storeDetail/{orderId}")
    public ResponseDTO<?> storeDetail(@PathVariable Long orderId){
        return new ResponseDTO<>(HttpStatus.OK.value(),
                storeService.storeDetail(orderId));
    }

    @ApiOperation(value = "가게등록", notes = "매장명, 최소주문금액, 배경 이미지를 넣어 가게 정보 등록")
    @PostMapping("/")
    public ResponseDTO<?> insert(@AuthenticationPrincipal String userId,
                                 @RequestBody StoreInsertDTO storeDTO,
                                 MultipartFile file) throws IOException {
        String imgPath = s3Service.upload(file);
        storeDTO.setBackgroundImageUrl(imgPath);

        return new ResponseDTO<>(HttpStatus.OK.value(), storeService.insert(userId, storeDTO));
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

    @ApiOperation(value = "카테고리별 가게 조회", notes = "")
    @PostMapping("/category")
    public ResponseDTO<?> selectCategory(@RequestBody CategoryDTO categoryDTO){
        return new ResponseDTO<>(HttpStatus.OK.value(),
                storeService.selectCategory(categoryDTO));
    }

    @ApiOperation(value = "가게정보 수정", notes = "id, name, minOrderPrice, backgroundImageUrl, category, infor을 받는다.")
    @PutMapping("/")
    public ResponseDTO<?> update(@AuthenticationPrincipal String userId,
                                 @RequestBody StoreInsertDTO storeDTO){
        return new ResponseDTO<>(HttpStatus.OK.value(), storeService.update(userId, storeDTO));
    }

    @ApiOperation(value = "가게정보 삭제", notes = "storeId를 받아서 가게정보를 삭제한다.")
    @DeleteMapping("/")
    public ResponseDTO<?> delete(@AuthenticationPrincipal String userId){
        return new ResponseDTO<>(HttpStatus.OK.value(), storeService.delete(userId)+"이(가) 삭제 되었습니다.");
    }

    @ApiOperation(value = "카테고리 가져오기", notes = "")
    @GetMapping("/category")
    public ResponseDTO<?> selectCategory(){
        List<CategoryDTO> categoryDTOS = new ArrayList<>();
        for(Category c : Category.values()){
            categoryDTOS.add(CategoryDTO.builder()
                            .category(c)
                            .category_ko(c.label())
                            .build());
        }

        return new ResponseDTO<>(HttpStatus.OK.value(), categoryDTOS);
    }
}
