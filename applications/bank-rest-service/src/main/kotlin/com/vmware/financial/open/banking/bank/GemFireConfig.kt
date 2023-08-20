package com.vmware.financial.open.banking.bank

import com.vmware.financial.open.banking.bank.domain.Bank
import com.vmware.financial.open.banking.bank.repository.BankRepository
import org.apache.geode.cache.DataPolicy
import org.apache.geode.cache.GemFireCache
import org.apache.geode.pdx.ReflectionBasedAutoSerializer
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.data.gemfire.GemfireTemplate
import org.springframework.data.gemfire.client.ClientRegionFactoryBean
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication
import org.springframework.data.gemfire.config.annotation.EnableClusterDefinedRegions
import org.springframework.data.gemfire.config.annotation.EnableContinuousQueries
import org.springframework.data.gemfire.config.annotation.EnablePdx
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories
import org.springframework.data.gemfire.transaction.config.EnableGemfireCacheTransactions


@Configuration
@ClientCacheApplication(subscriptionEnabled = true)
@EnableContinuousQueries
@EnableGemfireRepositories(basePackageClasses = [BankRepository::class])
@EnableGemfireCacheTransactions
@EnablePdx
class GemFireConfig {
    @Bean
    fun serializer() : ReflectionBasedAutoSerializer {
        return ReflectionBasedAutoSerializer(".*")
    }


    @Bean
    fun bankRegion(gemFireCache: GemFireCache) : ClientRegionFactoryBean<String, Bank>
    {
        var region = ClientRegionFactoryBean<String,Bank>()
        region.cache = gemFireCache
        region.setDataPolicy(DataPolicy.EMPTY)
        region.setRegionName("Bank")
        return region

    }

    @Bean
    fun gemFireTemplate(region: ClientRegionFactoryBean<String,Bank>) : GemfireTemplate
    {
        return GemfireTemplate(region.region);
    }
}