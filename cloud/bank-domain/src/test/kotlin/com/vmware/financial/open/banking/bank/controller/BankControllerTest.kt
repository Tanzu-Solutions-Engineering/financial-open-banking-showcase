package com.vmware.financial.open.banking.bank.controller

import com.vmware.financial.open.banking.bank.domain.Bank
import com.vmware.financial.open.banking.bank.service.BankService
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator
import org.apache.http.HttpStatus
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.util.*

internal class BankControllerTest{
    private lateinit var bankService: BankService
    private lateinit var bank : Bank
    private lateinit var subject : BankController

    @BeforeEach
    internal fun setUp() {
        bankService = mock<BankService>()
        bank = JavaBeanGeneratorCreator.of(Bank::class.java).create()
        subject = BankController(bankService)

    }

    @Test
    internal fun createBank() {
        whenever(bankService.createBank(bank)).thenReturn(bank)
        var actual = subject.createBank(bank)
        assertEquals(HttpStatus.SC_OK,actual.statusCode.value())
        assertEquals(bank,actual.body);
    }

    @Test
    internal fun getBank() {
        whenever(bankService.getBank(any<String>())).thenReturn(Optional.of(bank))
        var actual  = subject.getBank(bank.id)
        assertEquals(HttpStatus.SC_OK,actual.statusCode.value())
        assertEquals(bank,actual.body);
    }

    @Test
    internal fun getBank_doesNotExist_Returns401() {
        whenever(bankService.getBank(any<String>())).thenReturn(Optional.empty())
        var actual  = subject.getBank(bank.id)
        assertEquals(HttpStatus.SC_NOT_FOUND,actual.statusCode.value())
    }
}