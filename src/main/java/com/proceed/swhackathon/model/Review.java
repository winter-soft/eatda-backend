package com.proceed.swhackathon.model;

import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Review extends TimeZone {

    @Id @GeneratedValue
    @Column(name = "review_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userOrderDetail_id")
    private UserOrderDetail userOrderDetail; // 주문내역을 가져오기 위한 uod

    @Column(nullable = true)
    private String imageUrl; // 음식 사진

    @Column(nullable = false)
    private int star; // 1~5의 정수값(?)

    @Column(nullable = false)
    private String content; // 리뷰내용

    @CreatedBy
    @Column(nullable = false)
    private String createdBy; // 작성자
}
