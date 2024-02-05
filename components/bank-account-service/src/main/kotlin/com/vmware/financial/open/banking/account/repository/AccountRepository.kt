package com.vmware.financial.open.banking.account.repository

import com.vmware.financial.open.banking.domain.account.BankAccount
import org.springframework.data.keyvalue.repository.KeyValueRepository
import org.springframework.data.repository.CrudRepository


    interface AccountRepository : KeyValueRepository<BankAccount, String> {
    }

