package com.vmware.financial.open.banking.account

import com.vmware.financial.open.banking.account.domain.Account
import org.apache.geode.cache.GemFireCache
import org.apache.geode.cache.client.ClientCacheFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.gemfire.GemfireTemplate
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication
import org.springframework.data.gemfire.config.annotation.EnableClusterDefinedRegions

/**
 * @author Gregory Green
 */
@Configuration
@ClientCacheApplication
@EnableClusterDefinedRegions
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