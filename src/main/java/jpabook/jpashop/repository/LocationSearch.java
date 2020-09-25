package jpabook.jpashop.repository;

import jpabook.jpashop.domain.RoomType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocationSearch {

    private String locationName;
    private RoomType roomType;
    private String locationStockQuantity;

}
