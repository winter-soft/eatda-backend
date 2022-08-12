package com.proceed.swhackathon.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.proceed.swhackathon.exception.store.StoreDistanceException;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@ToString(exclude = "likes")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Store {

    @Id @GeneratedValue
    @Column(name = "store_id")
    private Long id;

    private String name; // 매장명
    private int minOrderPrice; // 최소 주문금액
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recentlyOrder_id")
    private Order recentlyOrder; // 가장 최근 주문

    @Lob
    private String backgroundImageUrl; // 배경 이미지
    @Enumerated(EnumType.STRING)
    private Category category; // 매장 카테고리

    @Lob
    private String infor; // 가게 정보

    private String distance; // 거리정보

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user; // 사장님

    @OneToMany(mappedBy = "store")
    @Builder.Default
    @JsonIgnore
    private List<Menu> menus = new ArrayList<>();

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    @Builder.Default
    @JsonIgnore
    private List<Likes> likes = new ArrayList<>();

    // 메뉴 삭제
    public void removeMenu(){
        for(Menu m : menus){
            m.removeStore();
        }
        menus = null;
    }

    // 메뉴 설정
    public void addMenu(Menu menu){
        this.menus.add(menu);
    }

    public void deleteMenu(Menu menu){
        if(this.menus.contains(menu)) {
            this.menus.remove(menu);
        }
    }

    // 좋아요 설정
    public void addLike(Likes like){
        this.likes.add(like);
    }

    public void deleteLike(Likes like){
        if(this.likes.contains(like)) {
            this.likes.remove(like);
        }
    }

    public int getLikesCount(){
        return likes.size();
    }

    // Set recentlyOrder
    public void setRecentlyOrder(Order order){
        if(order.getOrderStatus() == OrderStatus.WAITING){
            recentlyOrder = order;
        }
    }

    // Set distance
    public void setDistance(String d){
        if(!d.substring(d.length()-2, d.length()).equals("km")){
            throw new StoreDistanceException();
        }
        distance = d;
    }
}
