package com.vmware.financial.open.banking.bank.sink

import com.vmware.financial.open.banking.bank.domain.Bank
import com.vmware.financial.open.banking.bank.service.BankService
import org.springframework.stereotype.Service
import java.util.function.Consumer

/**
 * @author Gregory Green
 */
@Service
class BankConsumer(private val bankService: BankService) : Consumer<Bank> {
    override fun accept(bank: Bank) {
        bankService.createBank(bank)
    }
}