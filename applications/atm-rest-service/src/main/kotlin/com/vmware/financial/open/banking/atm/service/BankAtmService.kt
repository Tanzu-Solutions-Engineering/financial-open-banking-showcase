package com.vmware.financial.open.banking.atm.service

import com.vmware.financial.open.banking.atm.domain.Atm
import org.springframework.data.gemfire.GemfireTemplate

/**
 * @author Gregory Green
 */
class BankAtmService(private val gemfireTemplate: GemfireTemplate) : AtmService {
    override fun createAtm(bankId: String, atm: Atm): Atm {
        gemfireTemplate.put("${bankId}|${atm.id}",atm)
        return atm
    }

    override fun getAtm(bankId: String, atmId: String): Atm {
        return gemfireTemplate.get("${bankId}|${atmId}")
    }
}