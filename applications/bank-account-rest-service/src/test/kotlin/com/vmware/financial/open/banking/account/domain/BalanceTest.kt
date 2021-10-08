package com.vmware.financial.open.banking.account.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.math.BigDecimal

internal class BalanceTest{


    @Test
    internal fun given_balanceWithAmtBigDecimal_thenConvert() {

        val expected = BigDecimal("10.50")
        var subject  = Balance(expected)
        assertEquals(expected,subject.amount)
    }

    @Test
    internal fun given_balanceWithAmtDouble_thenConvert() {

        val expected = 10.50
        var subject  = Balance(expected)
        assertEquals(expected,subject.amount.toDouble())
    }
}