package com.vmware.financial.open.banking.bank.sink

import com.vmware.financial.open.banking.bank.domain.Bank
import com.vmware.financial.open.banking.bank.repository.BankRepository
import com.vmware.financial.open.banking.bank.service.BankService
import org.apache.geode.cache.DataPolicy
import org.apache.geode.cache.GemFireCache
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.gemfire.client.ClientRegionFactoryBean
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories

/**
 * @author Gregory Green
 */
@ClientCacheApplication
@EnableGemfireRepositories(basePackageClasses = [BankRepository::class])
@ComponentScan(basePackageClasses = [BankConsumer::class, BankService::class])
@Configuration
class GemFireConfig {

    @Value("\${spring.data.gemfire.pool.locators:localhost[10334}")
    private val locators = ""

    @Bean
    fun region(gemFireCache : GemFireCache) : ClientRegionFactoryBean<String,Bank>{
        var region = ClientRegionFactoryBean<String,Bank>()
        region.cache= gemFireCache
        region.setDataPolicy(DataPolicy.EMPTY)
        region.setRegionName("Bank")
        return region
    }
}