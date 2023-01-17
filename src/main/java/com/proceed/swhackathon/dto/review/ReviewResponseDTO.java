package com.proceed.swhackathon.dto.review;

import com.proceed.swhackathon.dto.menu.MenuNameDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponseDTO {
    private Long id; // id
    private String imageUrl; // 리뷰 사진
    private int star; // 별점
    private String content; // 리뷰 내용
    private String createdBy; // 작성자
    private List<MenuNameDTO> menuName = new ArrayList<>(); // 메뉴 이름
    private LocalDateTime orderDate; // 날짜
    private boolean visible;
    private String relativeDate; // 상대적인 날짜
}
