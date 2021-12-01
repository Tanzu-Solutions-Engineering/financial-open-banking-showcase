package com.vmware.financial.open.banking.account.repository

import com.vmware.financial.open.banking.account.domain.BankAccount
import org.springframework.data.repository.CrudRepository

interface AccountRepository : CrudRepository<BankAccount,String> {
}