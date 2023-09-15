package com.vmware.financial.open.banking.bank.sink

import com.vmware.financial.open.banking.account.service.AccountService
import com.vmware.financial.open.banking.domain.account.BankAccount
import org.springframework.stereotype.Service
import java.util.function.Consumer

/**
 * @author Gregory Green
 */
@Service
 class BankAccountConsumer(private val service: AccountService) : Consumer<BankAccount> {

    override fun accept(bankAccount: BankAccount) {
        service.createAccount(bankAccount)
    }
}