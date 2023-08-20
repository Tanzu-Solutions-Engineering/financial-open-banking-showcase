package com.vmware.financial.open.banking.bank.sink

import com.vmware.financial.open.banking.atm.domain.Atm
import com.vmware.financial.open.banking.atm.service.AtmService
import org.apache.geode.cache.DataPolicy
import org.apache.geode.cache.GemFireCache
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.gemfire.client.ClientRegionFactoryBean
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication

/**
 * @author Gregory Green
 */
@ClientCacheApplication
@ComponentScan(basePackageClasses = [AtmConsumer::class, AtmService::class])
@Configuration
class GemFireConfig {

    @Value("\${spring.data.gemfire.pool.locators:localhost[10334}")
    private val locators = ""

    @Bean
    fun region(gemFireCache : GemFireCache) : ClientRegionFactoryBean<String,Atm>{
        var region = ClientRegionFactoryBean<String, Atm>()
        region.cache= gemFireCache
        region.setDataPolicy(DataPolicy.EMPTY)
        region.setRegionName("Atm")
        return region
    }
}