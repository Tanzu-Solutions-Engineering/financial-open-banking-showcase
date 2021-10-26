package com.vmware.financial.open.banking.atm.service

import com.vmware.financial.open.banking.atm.domain.Atm
import java.util.*

/**
 * ATM service for banks interface
 */
interface AtmService {
    abstract fun createAtm(bankId : String,atm: Atm): Atm
    abstract fun getAtm(bankId: String, atmId: String): Optional<Atm>

}
