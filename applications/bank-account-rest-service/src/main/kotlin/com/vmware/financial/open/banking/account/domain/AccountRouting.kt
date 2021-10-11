package com.vmware.financial.open.banking.account.domain

data class AccountRouting(
    var address : String = "", var scheme : String = ""
    ) {
    constructor(accountRouting: Array<String>) : this()
    {
        if(accountRouting.isNotEmpty())
            address = accountRouting[0]
    }
}