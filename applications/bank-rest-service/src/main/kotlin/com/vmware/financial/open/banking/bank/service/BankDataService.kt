package com.vmware.financial.open.banking.bank.service

import com.vmware.financial.open.banking.bank.domain.Bank
import com.vmware.financial.open.banking.bank.repository.BankRepository
import org.springframework.stereotype.Service
import java.util.*

/**
 * Implementation for bank domain operations
 * @author Gregory Green
 */
@Service
class BankDataService(private val bankRepository: BankRepository) : BankService {
    override fun createBank(bank: Bank): Bank {
        return bankRepository.save(bank)
    }

    override fun getBank(bankId: String): Optional<Bank> {
        return bankRepository.findById(bankId)
    }
}