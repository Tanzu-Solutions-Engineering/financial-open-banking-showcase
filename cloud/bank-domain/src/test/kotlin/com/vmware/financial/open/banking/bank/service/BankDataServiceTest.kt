package com.vmware.financial.open.banking.bank.service

import com.vmware.financial.open.banking.bank.domain.Bank
import com.vmware.financial.open.banking.bank.repository.BankRepository
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.BeforeEach
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.util.*

/**
 * Testing for BankDataService
 * @author Gregory Green
 */
internal class BankDataServiceTest {

    private lateinit var subject : BankDataService
    private lateinit var bank : Bank
    private lateinit var bankRepository : BankRepository

    @BeforeEach
    internal fun setUp() {
        bankRepository = mock<BankRepository>()
        bank = JavaBeanGeneratorCreator.of(Bank::class.java).create()
        subject = BankDataService(bankRepository)

    }
    @Test
    fun createBank() {
        whenever(bankRepository.save(bank)).thenReturn(bank)
        assertEquals(bank,subject.createBank(bank));
    }

    @Test
    fun getBank() {
        whenever(bankRepository.findById(bank.id)).thenReturn(Optional.of(bank))
        var actual = subject.getBank(bank.id)
        assertEquals(bank,actual.get());
    }
}