package com.vmware.financial.open.banking.account.event

import com.vmware.financial.open.banking.gemfire.account.event.PremiumAccountCqListener
import org.apache.geode.cache.Operation
import org.apache.geode.cache.query.CqEvent
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

/**
 * @author Gregory Green
 */
class PremiumAccountCqListenerTest {
    @Test
    internal fun cq() {

        var subject = PremiumAccountCqListener()
        var cqEvent = mock<CqEvent>()

        val mockOperation = mock<Operation>()

        whenever(cqEvent.baseOperation).thenReturn(mockOperation)

        subject.handle(cqEvent)
    }
}