package jpabook.jpashop.controller;

import jpabook.jpashop.domain.RoomType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocationForm {

    private Long id;


    private String name;

    private RoomType roomType;

    private int stockQuantity;

}
