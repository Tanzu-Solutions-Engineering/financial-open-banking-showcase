package com.vmware.financial.open.banking.atm.domain

data class AtmAddress(
    var line_1 : String = "",
    var line_2 : String = "",
    var line_3 : String = "",
    var city : String = "",
    var county : String = "",
    var state : String = "",
    var postcode : String = "",
    var country_code : String = "",
)
