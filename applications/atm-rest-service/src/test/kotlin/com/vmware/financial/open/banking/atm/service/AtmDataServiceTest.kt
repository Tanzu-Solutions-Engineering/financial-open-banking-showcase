package com.vmware.financial.open.banking.atm.service

import com.vmware.financial.open.banking.atm.domain.Atm
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.BeforeEach
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.springframework.data.gemfire.GemfireTemplate
import java.util.*

internal class AtmDataServiceTest {
    private lateinit var gemfireTemplate: GemfireTemplate
    private lateinit var subject : AtmDataService
    private lateinit var atm : Atm

    @BeforeEach
    internal fun setUp() {
        atm = JavaBeanGeneratorCreator.of(Atm::class.java).create()
        gemfireTemplate = mock<GemfireTemplate>()
        subject = AtmDataService(gemfireTemplate)
    }

    @Test
    fun createAtm() {
        var actual = subject.createAtm(atm.bank_id,atm)
        assertEquals(atm,actual);
        verify(gemfireTemplate).put(any<String>(),any<Atm>())
    }

    @Test
    fun getAtm() {
        whenever(gemfireTemplate.get<String,Atm>(any<String>())).thenReturn(atm)
        var actual = subject.getAtm(atm.bank_id,atm.id)
        assertEquals(atm,actual.get());

    }

    @Test
    internal fun getAtmNotFound() {
        whenever(gemfireTemplate.get<String,Atm>(any<String>())).thenReturn(null)
        var actual = subject.getAtm(atm.bank_id,atm.id)
        assertEquals(Optional.empty<Atm?>(),actual);
    }
}