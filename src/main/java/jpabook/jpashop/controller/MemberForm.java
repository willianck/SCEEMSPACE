package jpabook.jpashop.controller;

import jpabook.jpashop.domain.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class MemberForm {

    private Long id;

    //    @NotEmpty(message = "Student number is essential.")
    private String name;

    private String department;

    private OrderStatus status;

    private String pathway;

    //    @NotEmpty(message = "Email address is essential.")
    private String email_address;

    @NotEmpty(message = "First name is essential.")
    private String first_name;

    @NotEmpty(message = "Last name is essential.")
    private String last_name;

    // newly created variable
    private String groupAllocation;

    private String role;

    private String supervisor;

    private String researchGroup;

    @NotEmpty(message = "Priority is essential.")
    private String priority;

    private String notes;
    // newly created variable

}
