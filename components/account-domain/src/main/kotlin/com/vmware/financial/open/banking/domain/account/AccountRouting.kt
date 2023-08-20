package com.vmware.financial.open.banking.domain.account

data class AccountRouting(
    var address : String = "", var scheme : String = ""
    ) {
    constructor(accountRouting: Array<String>) : this()
    {
        if(accountRouting.isNotEmpty())
            address = accountRouting[0]
    }
}