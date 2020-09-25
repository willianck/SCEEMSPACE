package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
public class OrderItem {
    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "location_id")
    private Location location;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private RoomType roomType; //주문가격

    private int count;  //주문수량

    private String deskNumber;

    // newly created variable
    private String endDate;
    // newly created variable

    protected OrderItem(){

    }
    //생성메서드
    public static OrderItem createOrderItem(Location location, RoomType roomType, int count, String deskNumber, String endDate){
        OrderItem orderItem = new OrderItem();
        orderItem.setLocation(location);
        orderItem.setRoomType(roomType);
        orderItem.setCount(count);
        orderItem.setDeskNumber(deskNumber);
        orderItem.setEndDate(endDate);

        location.removeStock(count);
        return orderItem;
    }

    //비지니스 로직
    public void cancel() {
        getLocation().addStock(count);
    }

//    //조회 로직
//    public int getTotalPrice() {
//        return getOrderPrice() * getCount();
//    }
}
