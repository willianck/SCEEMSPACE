package jpabook.jpashop.service;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.Location;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired EntityManager em;
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;

    @Test
    public void ALLOCATION() throws Exception{
        //given
        Member member = createMember();

        Location location = createBook("MVB.L0.410", RoomType.INDIVIDUAL, 10);

        int orderCount = 2;
        String deskNumber = "#300";
        String endDate = "27/10/2020";

        //when
        Long orderId = orderService.order(member.getId(), location.getId(), orderCount, deskNumber, endDate);

        //then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals("order status should be ALLOCATED", OrderStatus.ALLOCATED, getOrder.getStatus());
        assertEquals("allocated location should be correct", 1, getOrder.getOrderItems().size());
        assertEquals("expected number of stock should be 8", 8, location.getStockQuantity());
    }

    @Test
    public void DE_ALLOCATION() throws Exception{
        //given
        Member member = createMember();
        Location item = createBook("MVB.L0.410", RoomType.INDIVIDUAL, 10);

        int orderCount = 2;
        String deskNumber = "#300";
        String endDate = "27/10/2020";

        Long orderId = orderService.order(member.getId(), item.getId(), orderCount, deskNumber, endDate);

        //when
        orderService.cancelOrder(orderId);

        //then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals("order status should be UNALLOCATED", OrderStatus.DEALLOCATED, getOrder.getStatus());
        assertEquals("stock should be re-added after UNALLOCATED", 10, item.getStockQuantity());
    }

    @Test(expected = NotEnoughStockException.class)
    public void OVERFLOW_MAXIMUM_DESKS_AVAILABLE() throws Exception{
        //given
        Member member = createMember();
        Location location = createBook("MVB.L0.410", RoomType.INDIVIDUAL, 10);

        int orderCount = 11;
        String deskNumber = "300";
        String endDate = "27/10/2020";

        //when
        orderService.order(member.getId(), location.getId(), orderCount, deskNumber, endDate);

        //then
        fail("not enough stock exception should be occurred");
    }

    private Location createBook(String name, RoomType roomType, int stockQuantity) {
        Location location = new Location();
        location.setName(name);
        location.setRoomType(roomType);
        location.setStockQuantity(stockQuantity);
        em.persist(location);
        return location;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("jp17528");
        em.persist(member);
        return member;
    }

}