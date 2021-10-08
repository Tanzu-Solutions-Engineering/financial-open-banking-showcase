package com.vmware.financial.open.banking.account.controller

import com.vmware.financial.open.banking.account.domain.Account
import com.vmware.financial.open.banking.account.domain.AccountRouting
import com.vmware.financial.open.banking.account.domain.Balance
import com.vmware.financial.open.banking.account.service.AccountService
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
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

    private lateinit var account: Account

    private lateinit var accountService: AccountService

    private lateinit var subject : AccountController

    @BeforeEach
    internal fun setUp() {
        account = JavaBeanGeneratorCreator.of(Account::class.java).create()
        accountService = mock<AccountService>(){
            onGeneric { createAccount(any())} doReturn account
            onGeneric { findAccountById(anyString(),anyString())} doReturn Optional.of(account)
        }
        subject = AccountController(accountService)
    }


    @Test
    internal fun accountOverload() {
        val expectedAccountRoutings = "expectedAccountRoutings"
        val expectedViewsBasic = "expectedViewsBasic"


        account.account_routings = arrayOf(AccountRouting(scheme = expectedAccountRoutings))
        account.views_basic = arrayOf(expectedViewsBasic)

        var otherAccount = Account(id = account.id,
        bank_id= account.bank_id,
        label = account.label,
        number = account.number,
        product_code = account.product_code,
        balance  = account.balance,
        account_routings  = account.account_routings!![0].scheme,
        views_basic = account.views_basic!![0],
        key  = account.key)

        assertEquals(account,otherAccount);

    }

    @Test
    fun create() {
        val bankId = "expected"
        var response = subject.createAccount(bankId,account)
        verify(accountService).createAccount(account)
    }

    @Test
    internal fun getAccountById_foundAccount() {
        val bankId = "expected"
        var actualHttpEntity : ResponseEntity<Account> = subject.getAccountById(bankId,account.id)
        assertEquals(actualHttpEntity.statusCode ,HttpStatus.OK)
        assertEquals(account,actualHttpEntity.body);
    }

    @Test
    internal fun getAccountById_notFoundAccount() {

        accountService = mock<AccountService>(){
            onGeneric { findAccountById(anyString(),anyString())} doReturn Optional.empty()        }
        subject = AccountController(accountService)

        val bankId = "expected"
        var actualHttpEntity : ResponseEntity<Account> = subject.getAccountById(bankId,account.id)
        assertEquals(actualHttpEntity.statusCode ,HttpStatus.NOT_FOUND)
    }

}