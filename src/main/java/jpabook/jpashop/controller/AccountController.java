package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Account;
import jpabook.jpashop.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
public class AccountController {

    @Autowired
    AccountService accountService;

    @GetMapping("/account")
    public String index(Model model){
        model.addAttribute("accountsList", accountService.findAll());
        model.addAttribute("account", new Account());
        return "accountList";
    }

    @PostMapping("/account")
    public String create(@Valid Account account, BindingResult bindingResult, Model model){
        log.info("account : {}", account);

        if(bindingResult.hasErrors()) {
            model.addAttribute("account", account);
            return "accountList";
        }
        accountService.save(account);
        return "redirect:/account";
    }

    @GetMapping("/account/{accountId}/delete")
    public String deleteAccount(@PathVariable Long accountId){
        accountService.deleteAccount(accountId);
        return "redirect:/account";
    }
}
