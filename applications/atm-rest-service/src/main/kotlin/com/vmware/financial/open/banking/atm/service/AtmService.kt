package com.vmware.financial.open.banking.atm.service

import com.vmware.financial.open.banking.atm.domain.Atm

/**
 * ATM service for banks interface
 */
interface AtmService {
    abstract fun createAtm(bankId : String,atm: Atm): Atm
    abstract fun getAtm(bankId: String, atmId: String): Atm

}
