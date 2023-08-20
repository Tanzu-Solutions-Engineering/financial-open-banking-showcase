package com.vmware.financial.open.banking.bank.sink

import com.vmware.financial.open.banking.bank.domain.Bank
import com.vmware.financial.open.banking.bank.service.BankService
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator
import org.junit.jupiter.api.Test

import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

/**
 * BankConsumerTest test for BankConsumer
 */
internal class BankConsumerTest {

    @Test
    fun accept() {
        var bankService : BankService = mock<BankService>()
        var subject  = BankConsumer(bankService)
        val bank = JavaBeanGeneratorCreator.of(Bank::class.java).create()
        subject.accept(bank)
        verify(bankService).createBank(bank)
    }
}