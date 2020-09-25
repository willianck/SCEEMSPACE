package jpabook.jpashop.domain;

import org.springframework.security.core.GrantedAuthority;

public enum AccountAuthority implements GrantedAuthority {

    USER("USER"), ADMIN("ADMIN");

    String authority;

    AccountAuthority(String authority){
        this.authority = authority;
    }

    @Override
    public String getAuthority(){
        return null;
    }


}
