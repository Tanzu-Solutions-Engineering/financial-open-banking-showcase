package com.vmware.financial.open.banking.bank.sink

import com.vmware.financial.open.banking.atm.domain.Atm
import com.vmware.financial.open.banking.atm.service.AtmService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.function.Consumer

/**
 * @author Gregory Green
 */
@Service
class AtmConsumer(private val atmService: AtmService) : Consumer<Atm> {

    override fun accept(atm: Atm) {
        atmService.createAtm(atm.bank_id,atm)
    }
}