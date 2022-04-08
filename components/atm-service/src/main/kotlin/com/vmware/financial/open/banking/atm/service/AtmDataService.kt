package com.vmware.financial.open.banking.atm.service

import com.vmware.financial.open.banking.atm.domain.Atm
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.gemfire.GemfireTemplate
import org.springframework.stereotype.Service
import java.util.*

/**
 * @author Gregory Green
 */
@Service
class AtmDataService(private val gemfireTemplate: GemfireTemplate) : AtmService {

    var logger: Logger = LoggerFactory.getLogger(AtmDataService::class.java)

    override fun createAtm(bankId: String, atm: Atm): Atm {
        gemfireTemplate.put("$bankId|${atm.id}",atm)

        logger.info("Saving key: $bankId|${atm.id} ATM: $atm")

        return atm
    }

    override fun getAtm(bankId: String, atmId: String): Optional<Atm> {
        var atm: Atm? = gemfireTemplate.get<String,Atm?>("${bankId}|${atmId}") ?: return Optional.empty()

        return Optional.of(atm!!)
    }
}