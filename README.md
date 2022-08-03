# swhackathon-backend

## ERD

<img width="932" alt="스크린샷 2022-08-03 오전 10 24 50" src="https://user-images.githubusercontent.com/79779676/182504072-41ac02ce-5f35-4e0e-bff1-d020343514c8.png">

## 주문을 넣는 비즈니스 로직

- [X] Client가 가게 주문을 생성 (Order)
- [X] Client가 menu를 카트에 담는다. (담은 정보는 OrderDetail에 저장됨)
- [X] 가게에 주문을 한다. (OrderDetail을 User, Order 정보를 통해 담긴 메뉴를 모두 가져와 UserOrderDetail에 저장)
- [X] 이때 UserOrderDeatil에는 카트에 담은 totalPrice가 저장되고, Order의 현재 달성금액에 추가된다.
- [X] 사장은 달성 금액을 보고 가게 주문 상태를 변경한다. **(WAITING,ACCEPT,SHIPPING,COMPLETE,CANCEL)**
