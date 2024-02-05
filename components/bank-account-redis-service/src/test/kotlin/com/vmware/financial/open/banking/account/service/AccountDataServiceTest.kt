package com.vmware.financial.open.banking.account.service

import com.vmware.financial.open.banking.account.redis.service.AccountDataService
import com.vmware.financial.open.banking.domain.account.BankAccount
import com.vmware.financial.open.banking.domain.account.BankAccountCreateDto
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.ValueOperations
import java.util.*

/**
 * Test for AccountDataService
 * @author Gregory Green
 */
internal class AccountDataServiceTest {

    private val bankId = "unitTestBank"
    private lateinit var opsValue: ValueOperations<String,BankAccount>
    private lateinit var account: BankAccount
    private lateinit var template: RedisTemplate<String,BankAccount>
    private lateinit var subject : AccountDataService;
    private lateinit var accountDto : BankAccountCreateDto

    @BeforeEach
    internal fun setUp() {
        accountDto = JavaBeanGeneratorCreator.of(BankAccountCreateDto::class.java).create()
        account = JavaBeanGeneratorCreator.of(BankAccount::class.java).create()
        template = mock<RedisTemplate<String,BankAccount>>()
        opsValue = mock<ValueOperations<String,BankAccount>>()
        whenever(template.opsForValue()).thenReturn(opsValue)
        subject = AccountDataService(template)
    }

    @Test
    fun toKey() {
        assertEquals("${account.bank_id}|${account.id}",subject.toKey(account));
        assertEquals("${account.bank_id}|${account.id}",subject.toKey(account.bank_id,account.id));
    }


    @Test
    fun createAccount() {
        assertEquals(account,subject.createAccount(account))

        verify(opsValue)[any<String>()] = any<BankAccount>()
    }

    @Test
    internal fun toAccount() {
        var bankId = "myBank"
        var actual = subject.toAccount(bankId,accountDto)
        assertEquals(accountDto.account_id,actual.id)
        assertEquals(accountDto.account_id,actual.number)
        assertEquals(accountDto.account_routings,actual.account_routings)
        assertEquals(accountDto.balance,actual.balance)
        assertEquals(accountDto.label,actual.label)
        assertEquals(bankId,actual.bank_id);
    }

    @Test
    fun createAccount_givenBankAccountCreateDto() {

        var actual = subject.createAccount(bankId,accountDto);
        assertTrue(actual.account_id.isNotEmpty())

        verify(opsValue)[any<String>()] = any<BankAccount>()
    }

    @Test
    fun createAccount_givenBankAccountCreateDto_account_id_isEmpty_then_generate() {

        accountDto.account_id = ""
        assertTrue(accountDto.account_id.isEmpty())
        var actual = subject.createAccount(bankId,accountDto);
        assertTrue(actual.account_id.isNotEmpty())

        verify(opsValue)[any<String>()] = any<BankAccount>()
    }

    @Test
    fun findAccountById() {

        whenever(template.opsForValue()[any<String>()]).thenReturn(account)
        assertEquals(Optional.of(account),subject.findAccountById(account.bank_id,account.id));
    }

    @Test
    fun updateAccount_whenDoesNotExist_ThenReturnsEmpty() {
        assertEquals(Optional.empty<BankAccount>(),subject.updateAccount(account))
        verify(opsValue).setIfAbsent(any(),any())
    }


    @Test
    fun updateAccount_ThenAccounts() {
        assertEquals(Optional.empty<BankAccount>(),subject.updateAccount(account))
        verify(opsValue).setIfAbsent(any(),any())
    }


    @Test
    fun saveAccount_ThenAccounts() {
        assertEquals(account,subject.saveAccount(account))
        verify(opsValue).set(any(),any())
    }

    @Test
    internal fun findAccount_WhenPdx_Then_ReturnAccount() {


        whenever(opsValue[any()]).thenReturn(account)
        assertEquals(Optional.of(account),subject.findAccountById(account.bank_id,account.id));

    }
}