package com.vmware.financial.open.banking.account.service

import com.vmware.financial.open.banking.domain.account.BankAccount
import com.vmware.financial.open.banking.domain.account.BankAccountCreateDto
import java.util.*

interface AccountService {

    fun createAccount(bankId: String, account: BankAccountCreateDto) : BankAccountCreateDto
    fun createAccount(account: BankAccount) : BankAccount
    fun findAccountById(bankId: String, accountId: String): Optional<BankAccount>
    fun updateAccount(account: BankAccount): Optional<BankAccount>

}