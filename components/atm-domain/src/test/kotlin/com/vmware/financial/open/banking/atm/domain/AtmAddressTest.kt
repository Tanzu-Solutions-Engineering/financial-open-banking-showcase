package com.vmware.financial.open.banking.atm.domain

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class AtmAddressTest{
    @Test
    internal fun constructorWithString() {
        val expected = "123 Straight Street, City, State, 07035, USA "
        var subject = AtmAddress(expected)
        assertEquals("123 Straight Street",subject.line_1);
        assertEquals("City",subject.city);
        assertEquals("State",subject.state);
        assertEquals("07035",subject.postcode);
        assertEquals("USA",subject.country_code);
    }
}