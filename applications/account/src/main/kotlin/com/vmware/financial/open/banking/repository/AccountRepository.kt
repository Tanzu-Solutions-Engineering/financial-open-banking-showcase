package com.vmware.financial.open.banking.repository

import com.vmware.financial.open.banking.domain.Account
import org.springframework.data.repository.CrudRepository

interface AccountRepository : CrudRepository<Account,String> {
}