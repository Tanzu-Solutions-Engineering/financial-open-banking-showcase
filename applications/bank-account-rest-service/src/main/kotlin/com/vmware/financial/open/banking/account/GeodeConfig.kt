package com.vmware.financial.open.banking.account

import com.vmware.financial.open.banking.account.domain.BankAccount
import org.apache.geode.cache.DataPolicy
import org.apache.geode.cache.GemFireCache
import org.apache.geode.cache.client.ClientCacheFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.DependsOn
import org.springframework.data.gemfire.GemfireTemplate
import org.springframework.data.gemfire.client.ClientRegionFactoryBean
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication
import org.springframework.data.gemfire.config.annotation.EnableClusterDefinedRegions
import org.springframework.data.gemfire.config.annotation.EnableContinuousQueries

/**
 * @author Gregory Green
 */
@Configuration
@ClientCacheApplication(subscriptionEnabled = true)
@EnableContinuousQueries
class GeodeConfig {

    @Bean("bankAccountRegion")
    fun bankAccountRegion(gemFireCache: GemFireCache) : ClientRegionFactoryBean<String,BankAccount>
    {
        var bankAccountRegion = ClientRegionFactoryBean<String,BankAccount>()
        bankAccountRegion.cache = gemFireCache
        bankAccountRegion.setRegionName("BankAccount")
        bankAccountRegion.setDataPolicy(DataPolicy.EMPTY)
        return bankAccountRegion;
    }
}