package com.vmware.financial.open.banking.account.controller

import com.vmware.financial.open.banking.account.service.AccountService
import com.vmware.financial.open.banking.account.service.controller.AccountController
import com.vmware.financial.open.banking.domain.account.BankAccount
import com.vmware.financial.open.banking.domain.account.BankAccountCreateDto
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.util.*

internal class AccountControllerTest {

    private lateinit var account: BankAccount
    private lateinit var accountCreateDto: BankAccountCreateDto

    private lateinit var accountService: AccountService

    private lateinit var subject : AccountController

    @BeforeEach
    internal fun setUp() {
        account = JavaBeanGeneratorCreator.of(BankAccount::class.java).create()
        accountCreateDto = JavaBeanGeneratorCreator.of(BankAccountCreateDto::class.java).create()
        accountService = mock<AccountService>(){
            onGeneric { createAccount(any<String>(),any<BankAccountCreateDto>())} doReturn accountCreateDto
            onGeneric { findAccountById(anyString(),anyString())} doReturn Optional.of(account)
        }
        subject = AccountController(accountService)
    }



    @Test
    fun create() {
        val bankId = "expected"
        var response = subject.createAccount(bankId,accountCreateDto)
        verify(accountService).createAccount(any<String>(),any())
    }

    @Test
    internal fun getAccountById_foundAccount() {
        val bankId = "expected"
        var actualHttpEntity : ResponseEntity<BankAccount> = subject.getAccountById(bankId,account.id)
        assertEquals(actualHttpEntity.statusCode ,HttpStatus.OK)
        assertEquals(account,actualHttpEntity.body);
    }

    @Test
    internal fun getAccountById_notFoundAccount() {

        accountService = mock<AccountService>(){
            onGeneric { findAccountById(anyString(),anyString())} doReturn Optional.empty()        }
        subject = AccountController(accountService)

        val bankId = "expected"
        var actualHttpEntity : ResponseEntity<BankAccount> = subject.getAccountById(bankId,account.id)
        assertEquals(actualHttpEntity.statusCode ,HttpStatus.NOT_FOUND)
    }

}