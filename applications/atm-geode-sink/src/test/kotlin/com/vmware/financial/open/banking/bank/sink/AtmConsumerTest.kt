package com.vmware.financial.open.banking.bank.sink

import com.vmware.financial.open.banking.atm.domain.Atm
import com.vmware.financial.open.banking.atm.service.AtmService
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

/**
 * BankConsumerTest test for AtmConsumer
 */
internal class AtmConsumerTest {

    @Test
    fun accept() {
        var atmService : AtmService = mock<AtmService>()
        var subject  = AtmConsumer(atmService)
        val atm = JavaBeanGeneratorCreator.of(Atm::class.java).create()
        subject.accept(atm)
        verify(atmService).createAtm(atm.bank_id,atm)
    }
}