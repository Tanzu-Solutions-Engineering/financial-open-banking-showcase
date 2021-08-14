package com.vmware.financial.open.banking.atm

import com.vmware.financial.open.banking.atm.domain.Atm
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
@ClientCacheApplication
@EnableClusterDefinedRegions
@Configuration
class GeodeConf {

    @Bean
    fun atmGemFireTemplate(gemFireCache: GemFireCache) : GemfireTemplate
    {
        return GemfireTemplate<String,Atm>(ClientCacheFactory.getAnyInstance().getRegion("Atm"))
    }
}