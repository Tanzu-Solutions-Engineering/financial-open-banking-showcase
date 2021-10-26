package com.vmware.financial.open.banking.atm.service

import com.vmware.financial.open.banking.atm.domain.Atm
import org.springframework.data.gemfire.GemfireTemplate
import org.springframework.stereotype.Service
import java.util.*

/**
 * @author Gregory Green
 */
@Service
class AtmDataService(private val gemfireTemplate: GemfireTemplate) : AtmService {
    override fun createAtm(bankId: String, atm: Atm): Atm {
        gemfireTemplate.put("${bankId}|${atm.id}",atm)
        return atm
    }

    override fun getAtm(bankId: String, atmId: String): Optional<Atm> {
        var atm: Atm? = gemfireTemplate.get<String,Atm?>("${bankId}|${atmId}") ?: return Optional.empty()

        return Optional.of(atm!!)
    }
}