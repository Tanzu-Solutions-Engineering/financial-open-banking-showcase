package com.vmware.financial.open.banking.account.service

import com.vmware.financial.open.banking.account.domain.Account
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator
import org.apache.geode.pdx.PdxInstance
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.mockito.kotlin.*
import org.springframework.data.gemfire.GemfireTemplate
import java.util.*
import kotlin.math.exp

/**
 * Test for AccountDataService
 * @author Gregory Green
 */
internal class AccountDataServiceTest {

    private val account: Account = JavaBeanGeneratorCreator.of(Account::class.java).create()
    private lateinit var gemFireTemplate: GemfireTemplate
    private lateinit var subject : AccountDataService;

    @BeforeEach
    internal fun setUp() {
        gemFireTemplate = mock<GemfireTemplate>()
        subject = AccountDataService(gemFireTemplate)
    }

    @Test
    fun toKey() {
        assertEquals("${account.bank_id}|${account.id}",subject.toKey(account));
        assertEquals("${account.bank_id}|${account.id}",subject.toKey(account.bank_id,account.id));
    }


    @Test
    fun createAccount() {
        assertEquals(account,subject.createAccount(account));
        verify(gemFireTemplate).put(any<String>(),any<Account>())
    }

    @Test
    fun findAccountById() {
        whenever(gemFireTemplate.get<Any,Any>(any())).thenReturn(account)
        assertEquals(Optional.of(account),subject.findAccountById(account.bank_id,account.id));
    }

    @Test
    fun updateAccount_whenDoesNotExist_ThenReturnsEmpty() {
        assertEquals(Optional.empty<Account>(),subject.updateAccount(account))
        verify(gemFireTemplate).containsKeyOnServer(any<String>())
    }

    @Test
    internal fun findAccount_WhenPdx_Then_ReturnAccount() {

        var accountPdx = mock<PdxInstance>()
        {
            on { `object` } doReturn account
        }
        whenever(gemFireTemplate.get<Any,Any>(any())).thenReturn(accountPdx)
        assertEquals(Optional.of(account),subject.findAccountById(account.bank_id,account.id));

    }
}