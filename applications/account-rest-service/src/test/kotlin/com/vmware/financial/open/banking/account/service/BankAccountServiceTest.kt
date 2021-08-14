package com.vmware.financial.open.banking.account.service

import com.vmware.financial.open.banking.account.domain.Account
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.springframework.data.gemfire.GemfireTemplate
import java.util.*

/**
 * Test for BankAccountService
 * @author Gregory Green
 */
internal class BankAccountServiceTest{

    private lateinit var gemFireTemplate: GemfireTemplate
    private lateinit var account: Account

    private lateinit var subject : BankAccountService

    @BeforeEach
    internal fun setUp() {
        account = JavaBeanGeneratorCreator.of(Account::class.java).create()
        gemFireTemplate = mock<GemfireTemplate>(){
            on{ get<String,Account>(any()) } doReturn  account
            on{ containsKeyOnServer(any<String>())} doReturn true;
        }
        subject = BankAccountService(gemFireTemplate)
    }
    @Test
    internal fun toKey() {
        assertEquals("${account.bank_id}|${account.id}",subject.toKey(account.bank_id,account.id))
    }
    @Test
    internal fun toKeyWithAccount() {
        assertEquals("${account.bank_id}|${account.id}",subject.toKey(account))
    }

    @Test
    internal fun createAccount() {
        var actual = subject.createAccount(account)
        assertEquals(account,actual)
        verify(gemFireTemplate).put(anyString(),any<Account>())
    }


    @Test
    internal fun findAccountById() {
        var actual = subject.findAccountById(account.bank_id,account.id)
        val expected = Optional.of(account)
        assertEquals(expected,actual);
    }

    @Test
    internal fun findAccountByIdNotFound() {
        gemFireTemplate = mock<GemfireTemplate>(){
            on{ get<String,Account>(any()) } doReturn  null
        }
        subject = BankAccountService(gemFireTemplate)

        var actual = subject.findAccountById(account.bank_id,account.id)
        assertTrue(actual.isEmpty)
    }

    @Test
    internal fun updateAccount() {

        var actual = subject.updateAccount(account)
        assertTrue(actual.isPresent)
        assertEquals(account,actual.get())

        verify(gemFireTemplate).containsKeyOnServer(any())
        gemFireTemplate.put(any<String>(),any<Account>())
    }

    @Test
    internal fun updateAccount_returnEmpty_when_Key_notFound() {

        gemFireTemplate = mock<GemfireTemplate>(){
            on{ get<String,Account>(any()) } doReturn  account
            on{ containsKeyOnServer(any<String>())} doReturn false;
        }
        subject = BankAccountService(gemFireTemplate)

        var actual = subject.updateAccount(account)
        assertTrue(actual.isEmpty)

    }
}