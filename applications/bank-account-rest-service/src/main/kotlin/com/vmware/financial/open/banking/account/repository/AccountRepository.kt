package com.vmware.financial.open.banking.account.repository

import com.vmware.financial.open.banking.account.domain.Account
import org.springframework.data.repository.CrudRepository

interface AccountRepository : CrudRepository<Account,String> {
}