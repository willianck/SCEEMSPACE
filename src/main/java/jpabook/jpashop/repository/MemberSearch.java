package jpabook.jpashop.repository;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberSearch {
    private String memberName;
    private String memberPriority;
    private String memberDepartment;
    private String memberPathway;
    private String memberGroup;
}
