package jpabook.jpashop.service;

import jpabook.jpashop.domain.Account;
import jpabook.jpashop.domain.AccountAuthority;
import jpabook.jpashop.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@Transactional
@Service
public class AccountService implements UserDetailsService {

    @Autowired
    private AccountRepository repository;

    @Autowired
    PasswordEncoder encoder;

    public void deleteAccount(Long accountId){
        repository.deleteById(accountId);
    }

    public List<Account> findAll() {
        return repository.findAll();
    }

    public Account save(Account account) {
        HashSet<AccountAuthority> a = new HashSet<>();
        a.add(AccountAuthority.ADMIN);
        if(account.getUsername().equals("jp17528@bristol.ac.uk")){
            a.add(AccountAuthority.ADMIN);
        }
        account.setAuthorities(a);
        account.setPassword(encoder.encode((account.getPassword())));
        return repository.save(account);
    }

    static class UserDetailImpl extends User {
        public UserDetailImpl(Account a){
            super(a.getUsername(), a.getPassword(), a.getAuthorities());
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username)
                .map(UserDetailImpl::new)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
