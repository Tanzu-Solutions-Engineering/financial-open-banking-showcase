package com.vmware.financial.open.banking.account

import com.vmware.financial.open.banking.account.domain.Account
import org.apache.geode.cache.GemFireCache
import org.apache.geode.cache.client.ClientCacheFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.gemfire.GemfireTemplate
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication
import org.springframework.data.gemfire.config.annotation.EnableClusterDefinedRegions
import org.springframework.data.gemfire.config.annotation.EnableContinuousQueries

/**
 * @author Gregory Green
 */
@Configuration
@ClientCacheApplication(subscriptionEnabled = true)
@EnableClusterDefinedRegions
@EnableContinuousQueries
class GeodeConfig {
    @Bean
    fun gemfireTemple(gemFireCache : GemFireCache) : GemfireTemplate
    {
        return GemfireTemplate<String,Account>(
            ClientCacheFactory
                .getAnyInstance()
                .getRegion("Account"))
    }
}