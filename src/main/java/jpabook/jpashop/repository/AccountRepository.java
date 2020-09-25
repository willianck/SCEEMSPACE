package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.Query;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByUsername(String username);


}
