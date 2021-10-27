package com.vmware.financial.open.banking.atm.domain

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class AtmHoursTest{
    @Test
    internal fun hoursParser() {
        val expectedStart = "9:00 AM"
        val expectedEnd = "9:00 PM"
        var subject = AtmHours("$expectedStart - $expectedEnd")

        assertEquals(expectedStart,subject.opening_time);
        assertEquals(expectedEnd,subject.closing_time);
    }

    @Test
    internal fun hoursNoDashParser() {
        val expectedStart = "9:00 AM"
        val expectedEnd = "9:00 PM"
        val expected = "$expectedStart $expectedEnd"
        var subject = AtmHours(expected)

        assertEquals(expected,subject.opening_time);
    }
}