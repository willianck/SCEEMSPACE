package jpabook.jpashop.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    private String department;

    private String pathway;

    @Enumerated(EnumType.STRING)
    private OrderStatus status; //주문상태

    //newly created variable
    private String email_address;

    private String first_name;

    private String last_name;
    //newly created variable

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    // newly created variable
    private String groupAllocation;

    private String role;

    private String supervisor;

    private String researchGroup;

    private String priority;

    private String notes;
    // newly created variable

}
