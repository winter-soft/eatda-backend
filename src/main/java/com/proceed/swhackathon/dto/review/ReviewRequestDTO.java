package com.proceed.swhackathon.dto.review;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRequestDTO {
    private int star;
    private String content;
    private MultipartFile image;
}
