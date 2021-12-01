package com.vmware.financial.open.banking.account.event

import org.apache.geode.cache.query.CqEvent
import org.apache.logging.log4j.LogManager
import org.springframework.data.gemfire.listener.annotation.ContinuousQuery
import org.springframework.stereotype.Component

/**
 * @author Gregory Green
 */
    @Component
    class PremiumAccountCqListener {
        private var log = LogManager.getLogger(PremiumAccountCqListener::class.java)

        @ContinuousQuery(name="AccountCq",
            query = "select * from /BankAccount where balance.amount > 100000 "+
                    "and (bank_id = 'VMware' or bank_id = 'SPRINGONE')")
        fun handle(cqEvent: CqEvent) {
            var eventOperation = cqEvent.baseOperation
            var key = cqEvent.key

            if(eventOperation.isDestroy) {
                log.warn("Premium Balance BankAccount $key DELETED!!!")
                return
            }

            var newValue = cqEvent.newValue
            log.info("Premium BankAccount $key operation ${eventOperation} executed resulting in $newValue")

        }
    }