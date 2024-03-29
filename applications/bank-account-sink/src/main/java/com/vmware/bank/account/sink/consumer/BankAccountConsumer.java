package com.vmware.bank.account.sink.consumer;

import com.vmware.financial.open.banking.account.service.AccountService;
import com.vmware.financial.open.banking.domain.account.BankAccount;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
@Slf4j
public class BankAccountConsumer implements Consumer<BankAccount> {
    private final AccountService accountService;

    @Override
    public void accept(BankAccount bankAccount) {

        try {
            log.info("save {} ", bankAccount);
            accountService.saveAccount(bankAccount);
        }
        catch (RuntimeException e)
        {
            log.error("Cannot save account:"+bankAccount,e);
        }
    }
}
