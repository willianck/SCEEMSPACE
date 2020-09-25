package jpabook.jpashop.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "orders")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "location_id")
    private Location location;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    private LocalDateTime allocatedDate;    //주문시간

    private String endDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status; //주문상태


    public void setMember(Member member){
        this.member = member;
        member.getOrders().add(this);
    }

    public void setLocation(Location location){
        this.location = location;
        location.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    //생성메서드
    public static Order createOrder(Member member, Location location, String endDate, OrderItem... orderItems){
        Order order = new Order();
        order.setMember(member);
        order.setLocation(location);
        order.setEndDate(endDate);
        for (OrderItem orderItem: orderItems){
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ALLOCATED);
        order.setAllocatedDate(LocalDateTime.now());
        return order;
    }

    //비지니스로직
    //주문취소
    public void cancel(){
        this.setStatus(OrderStatus.DEALLOCATED);
        for (OrderItem orderItem : orderItems){
            orderItem.cancel();
        }
    }

}

