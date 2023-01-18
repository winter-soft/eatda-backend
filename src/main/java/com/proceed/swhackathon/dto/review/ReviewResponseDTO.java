package com.proceed.swhackathon.dto.review;

import com.proceed.swhackathon.dto.menu.MenuNameDTO;
import com.proceed.swhackathon.utils.DateDistance;
import com.proceed.swhackathon.utils.UserNickName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    // ReviewResponseDTO의 작성자를 닉네임으로 바꿔주고, 상대적인 날짜를 계산해주는 static method
    public static List<ReviewResponseDTO> of(List<ReviewResponseDTO> reviewResponses) {

        return reviewResponses
                .stream().map((x) -> {
                    x.setRelativeDate(DateDistance.of(x.getOrderDate())); // 상대적인 날짜 계산
                    x.setCreatedBy(UserNickName.of(x.getCreatedBy())); // 닉네임으로 변경

                    return x;
                }).collect(Collectors.toList());
    }
}
