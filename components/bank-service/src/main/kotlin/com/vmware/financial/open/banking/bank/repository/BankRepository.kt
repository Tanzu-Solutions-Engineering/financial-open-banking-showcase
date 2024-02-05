package com.vmware.financial.open.banking.bank.repository

import com.vmware.financial.open.banking.bank.domain.Bank
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository


    @Repository
    interface BankRepository : CrudRepository<Bank,String> {
    }