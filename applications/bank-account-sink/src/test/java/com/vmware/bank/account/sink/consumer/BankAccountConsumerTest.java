package com.vmware.bank.account.sink.consumer;

import com.vmware.financial.open.banking.account.repository.AccountRepository;
import com.vmware.financial.open.banking.account.service.AccountService;
import com.vmware.financial.open.banking.domain.account.BankAccount;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator.of;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BankAccountConsumerTest {

    @Mock
    private AccountService repository;
    private BankAccountConsumer subject;
    private BankAccount account = of(BankAccount.class).create();

    @Test
    void apply() {
        subject = new BankAccountConsumer(repository);

        subject.accept(account);

        verify(repository).saveAccount(any(BankAccount.class));
    }
}