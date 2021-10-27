package com.vmware.financial.open.banking.atm.domain

data class AtmHours(
    var opening_time :  String = "",
    var closing_time :  String = "")
{
    constructor(times : String) : this("","")
    {
        var tokens = times.split("-")
        if(tokens.isNotEmpty())
            this.opening_time = tokens[0].trim()

        if(tokens.size > 1)
            this.closing_time = tokens[1].trim()
    }
}