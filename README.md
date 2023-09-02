# 잇다 API Server

대학교 기숙사생들을 위한 공동배달 서비스 : 잇다

## ERD

<img width="1684" alt="스크린샷 2023-09-03 오전 12 10 55" src="https://github.com/winter-soft/eatda-backend/assets/79779676/0730724d-ac0f-48eb-aa40-b562992395a9">


## 주문을 넣는 비즈니스 로직

- [X] Client가 가게 주문을 생성 (Order)
- [X] Client가 menu를 카트에 담는다. (담은 정보는 OrderDetail에 저장됨)
- [X] 가게에 주문을 한다. (OrderDetail을 User, Order 정보를 통해 담긴 메뉴를 모두 가져와 UserOrderDetail에 저장)
- [X] 이때 UserOrderDeatil에는 카트에 담은 totalPrice가 저장되고, Order의 현재 달성금액에 추가된다.
- [X] 사장은 달성 금액을 보고 가게 주문 상태를 변경한다. **(WAITING,ACCEPT,SHIPPING,COMPLETE,CANCEL)**
