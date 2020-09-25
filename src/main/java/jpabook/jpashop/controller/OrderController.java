package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Location;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.service.LocationService;
import jpabook.jpashop.service.MemberService;
import jpabook.jpashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final LocationService locationService;

    @GetMapping("/order")
    public String createForm(Model model){
        List<Member> members = memberService.findMembers();
        List<Location> items = locationService.findLocations();

        model.addAttribute("members", memberService.findMembers());
        model.addAttribute("byStudentNumber", Comparator.comparing(Member::getName));

        model.addAttribute("locations", locationService.findLocations());
        model.addAttribute("byItemName", Comparator.comparing(Location::getName));

        return "order/orderForm";
    }

    @PostMapping("/order")
    public String order(@RequestParam("memberId") Long memberId,
                        @RequestParam("locationId") Long locationId,
                        @RequestParam("count") int count,
                        @RequestParam("deskNumber") String deskNumber,
                        @RequestParam("endDate") String endDate
                        ){

        orderService.order(memberId, locationId, count, deskNumber, endDate);
        return "redirect:/orders";

    }

    @GetMapping("/orders")
    public String orderList(@ModelAttribute("orderSearch") OrderSearch orderSearch, Model model){
        List<Order> orders = orderService.findOrders(orderSearch);

        model.addAttribute("orders", orders);
        model.addAttribute("byEndDate", Comparator.comparing(Order::getEndDate));

        return "order/orderList";
    }

    @PostMapping("/orders/{orderId}/cancel")
    public String cancelOrder(@PathVariable("orderId") Long orderId){
        orderService.cancelOrder(orderId);
        return "redirect:/orders";
    }

}
