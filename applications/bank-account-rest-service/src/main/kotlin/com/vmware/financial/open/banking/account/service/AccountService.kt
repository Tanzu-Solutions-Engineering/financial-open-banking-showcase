package com.vmware.financial.open.banking.account.service

import com.vmware.financial.open.banking.account.domain.Account
import java.util.*

interface AccountService {
    fun createAccount(account: Account) : Account
    fun findAccountById(bankId: String, accountId: String): Optional<Account>
    fun updateAccount(account: Account): Optional<Account>

}