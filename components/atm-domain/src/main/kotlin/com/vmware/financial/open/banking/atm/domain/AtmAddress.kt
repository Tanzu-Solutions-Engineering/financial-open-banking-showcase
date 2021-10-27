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
){
    constructor(text : String) : this()
    {
        var tokens = text.split(",")
        if(tokens.isNotEmpty())
            line_1 = tokens[0].trim()

        if(tokens.size > 1)
            city = tokens[1].trim()

        if(tokens.size > 2)
            state = tokens[2].trim()

        if(tokens.size > 3)
            postcode = tokens[3].trim()

        if(tokens.size > 4)
            country_code = tokens[4].trim()
    }

}
