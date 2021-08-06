package com.vmware.financial.open.banking.domain

import java.math.BigDecimal
import java.util.*

data class Balance (
    var currency : Currency = Currency.getInstance(Locale.US),
    var amount : BigDecimal
)
