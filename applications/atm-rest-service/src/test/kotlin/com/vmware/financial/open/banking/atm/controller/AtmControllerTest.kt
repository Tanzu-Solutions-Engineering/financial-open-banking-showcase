package com.vmware.financial.open.banking.atm.controller

import com.vmware.financial.open.banking.atm.domain.Atm
import com.vmware.financial.open.banking.atm.service.AtmService
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.springframework.http.HttpStatus
import java.util.*

/**
 * Testing for AtmController
 * @author Gregory Green
 */
internal class AtmControllerTest {

    private lateinit var atm: Atm
    private val bankId: String = "fleet"
    private lateinit var atmService: AtmService
    private lateinit var subject: AtmController

    @BeforeEach
    internal fun setUp() {
        atmService = mock<AtmService>()

        atm = JavaBeanGeneratorCreator.of(Atm::class.java).create();
        subject = AtmController(atmService);
    }

    @Test
    fun createAtm() {
        whenever(atmService.createAtm(any<String>(), any<Atm>())).thenReturn(atm)
        var actual = subject.createAtm(bankId,atm)
        assertEquals(HttpStatus.OK,actual.statusCode)
        assertEquals(atm,actual.body);
    }

    @Test
    internal fun getAtm() {
        whenever(atmService.getAtm(any<String>(),any<String>())).thenReturn(Optional.of(atm))
        var actual = subject.getAtm(atm.bank_id,atm.id)
        assertEquals(atm,actual.body);
    }

    @Test
    internal fun getAtm_notFound_Return404() {
        whenever(atmService.getAtm(any<String>(),any<String>())).thenReturn(Optional.empty())
        var actual = subject.getAtm(atm.bank_id,atm.id)
        assertEquals(HttpStatus.NOT_FOUND,actual.statusCode);
    }
}