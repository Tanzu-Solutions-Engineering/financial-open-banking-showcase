package com.vmware.financial.open.banking.atm.domain

data class Atm(
    var id : String = "",
    var bank_id : String = "",
    var name : String = "",
    var address : AtmAddress = AtmAddress(),
    var meta  : AtmMeta = AtmMeta(),
    var monday : AtmHours = AtmHours(),
    var tuesday : AtmHours = AtmHours(),
    var wednesday : AtmHours = AtmHours(),
    var thursday : AtmHours = AtmHours(),
    var friday : AtmHours = AtmHours(),
    var saturday : AtmHours = AtmHours(),
    var sunday : AtmHours = AtmHours(),
    var is_accessible : Boolean = false,
    var located_at : String = "",
    var more_info : String = "",
    var has_deposit_capability : Boolean = false

)
