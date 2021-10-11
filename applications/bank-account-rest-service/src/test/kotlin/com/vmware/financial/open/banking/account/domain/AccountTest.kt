package com.vmware.financial.open.banking.account.domain

import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class AccountTest{

    private lateinit var subject: Account

    @BeforeEach
    internal fun setUp() {
        subject = JavaBeanGeneratorCreator.of(Account::class.java).create()
    }
    @Test
    internal fun accountOverload() {
        val expectedAccountRoutings = "expectedAccountRoutings"
        val expectedViewsBasic = "expectedViewsBasic"
        subject.account_routings = arrayOf(AccountRouting(address = expectedAccountRoutings))
        subject.views_basic = arrayOf(expectedViewsBasic)


        var otherAccount = Account(id = subject.id,
            bank_id= subject.bank_id,
            label = subject.label,
            number = subject.number,
            product_code = subject.product_code,
            balance  = subject.balance,
            account_routings  = arrayOf(subject.account_routings!![0].address!!),
            views_basic = subject.views_basic!!,
            key  = subject.key)

        assertEquals(subject,otherAccount);

    }

    @Test
    internal fun accountOverload_Strings() {
        val expectedAccountRoutings = "expectedAccountRoutings"
        val expectedViewsBasic = "expectedViewsBasic"
        subject.account_routings = arrayOf(AccountRouting(address = expectedAccountRoutings))
        subject.views_basic = arrayOf(expectedViewsBasic)


        var otherAccount = Account(id = subject.id,
            bank_id= subject.bank_id,
            label = subject.label,
            number = subject.number,
            product_code = subject.product_code,
            balance  = subject.balance,
            account_routings  = subject.account_routings!![0].address!!,
            views_basic = subject.views_basic!![0],
            key  = subject.key)

        assertEquals(subject,otherAccount);

    }
}