package com.vmware.spring.geode.showcase.controller;

import com.vmware.spring.geode.showcase.domain.Account;
import com.vmware.spring.geode.showcase.repostories.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@AllArgsConstructor
@RestController
public class AccountController
{
    private final AccountRepository accountRepository;

    @PostMapping("save")
    public <S extends Account> S save(@RequestBody S s)
    {
        return accountRepository.save(s);
    }

    @GetMapping("findById")
    public Optional<Account> findById(String s)
    {
        return accountRepository.findById(s);
    }

    @DeleteMapping("deleteById/{id}")
    public void deleteById(@PathVariable String id)
    {
        accountRepository.deleteById(id);
    }
}
