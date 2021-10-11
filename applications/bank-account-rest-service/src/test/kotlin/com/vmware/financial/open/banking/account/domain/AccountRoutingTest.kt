package com.vmware.financial.open.banking.account.domain

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class AccountRoutingTest{
    @Test
    internal fun overloadConstructor() {
        var arrayText :  Array<String> = arrayOf("hello")
        var accountRouting = AccountRouting (arrayText)
        assertEquals("hello",accountRouting.address)
    }
}