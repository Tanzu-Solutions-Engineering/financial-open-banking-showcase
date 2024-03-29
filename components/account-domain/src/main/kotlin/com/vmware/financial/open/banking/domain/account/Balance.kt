package com.vmware.financial.open.banking.domain.account

import java.math.BigDecimal
import java.util.*

data class Balance (
    var amount : BigDecimal = BigDecimal.ZERO,
    var currency : String = ""
) {
    constructor(amount: Double) : this(BigDecimal.valueOf(amount))
}
