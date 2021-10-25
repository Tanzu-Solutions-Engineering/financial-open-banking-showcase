package com.vmware.financial.open.banking.bank.service

import com.vmware.financial.open.banking.bank.domain.Bank
import java.util.*

/**
 * Abstraction for bank domain operations
 * @author Gregory Green
 */
interface BankService {
    abstract fun createBank(bank: Bank): Bank
    abstract fun getBank(bankId: String): Optional<Bank>
}