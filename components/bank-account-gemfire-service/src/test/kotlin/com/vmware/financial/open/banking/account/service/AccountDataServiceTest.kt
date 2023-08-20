package com.vmware.financial.open.banking.account.service

import com.vmware.financial.open.banking.domain.account.BankAccount
import com.vmware.financial.open.banking.domain.account.BankAccountCreateDto
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator
import org.apache.geode.pdx.PdxInstance
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*
import org.springframework.data.gemfire.GemfireTemplate
import java.util.*

/**
 * Test for AccountDataService
 * @author Gregory Green
 */
internal class AccountDataServiceTest {

    private val bankId = "unitTestBank"
    private lateinit var account: BankAccount
    private lateinit var gemFireTemplate: GemfireTemplate
    private lateinit var subject : AccountDataService;
    private lateinit var accountDto : BankAccountCreateDto

    @BeforeEach
    internal fun setUp() {
        accountDto = JavaBeanGeneratorCreator.of(BankAccountCreateDto::class.java).create()
        account = JavaBeanGeneratorCreator.of(BankAccount::class.java).create()
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
        verify(gemFireTemplate).put(any<String>(),any<BankAccount>())
    }

    @Test
    internal fun toAccount() {
        var bankId = "myBank"
        var actual = subject.toAccount(bankId,accountDto)
        assertEquals(accountDto.account_id,actual.id)
        assertEquals(accountDto.account_routings,actual.account_routings)
        assertEquals(accountDto.balance,actual.balance)
        assertEquals(accountDto.label,actual.label)
        assertEquals(bankId,actual.bank_id);
    }

    @Test
    fun createAccount_givenBankAccountCreateDto() {

        var actual = subject.createAccount(bankId,accountDto);
        assertTrue(actual.account_id.isNotEmpty())
        verify(gemFireTemplate).put(any<String>(),any<BankAccount>())
    }

    @Test
    fun createAccount_givenBankAccountCreateDto_account_id_isEmpty_then_generate() {

        accountDto.account_id = ""
        assertTrue(accountDto.account_id.isEmpty())
        var actual = subject.createAccount(bankId,accountDto);
        assertTrue(actual.account_id.isNotEmpty())
        verify(gemFireTemplate).put(any<String>(),any<BankAccount>())
    }

    @Test
    fun findAccountById() {
        whenever(gemFireTemplate.get<Any,Any>(any())).thenReturn(account)
        assertEquals(Optional.of(account),subject.findAccountById(account.bank_id,account.id));
    }

    @Test
    fun updateAccount_whenDoesNotExist_ThenReturnsEmpty() {
        assertEquals(Optional.empty<BankAccount>(),subject.updateAccount(account))
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