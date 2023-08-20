package com.vmware.financial.open.banking.domain.account

data class Location(
    var id: String = "",
    var address: String= "",
    var city: String = "",
    var stateCode: String = "",
    var zipCode: String = ""
)
