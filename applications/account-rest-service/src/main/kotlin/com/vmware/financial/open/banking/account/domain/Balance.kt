package com.vmware.financial.open.banking.account.domain

import java.math.BigDecimal
import java.util.*

data class Balance (
    var currency : String = "",
    var amount : BigDecimal = BigDecimal.ZERO
)
