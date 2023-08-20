package com.vmware.financial.open.banking.account

import com.vmware.financial.open.banking.account.repository.AccountRepository
import com.vmware.financial.open.banking.account.service.AccountDataService
import com.vmware.financial.open.banking.domain.account.BankAccount
import org.apache.geode.cache.DataPolicy
import org.apache.geode.cache.GemFireCache
import org.apache.geode.pdx.ReflectionBasedAutoSerializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.gemfire.GemfireTemplate
import org.springframework.data.gemfire.client.ClientRegionFactoryBean
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication
import org.springframework.data.gemfire.config.annotation.EnableContinuousQueries
import org.springframework.data.gemfire.config.annotation.EnablePdx
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories

/**
 * @author Gregory Green
 */
@Configuration
@EnableContinuousQueries
@EnableGemfireRepositories(basePackageClasses = [AccountRepository::class])
@ClientCacheApplication(subscriptionEnabled = true)
@EnablePdx
class GemFireConfig {

    @Bean
    fun serializer() : ReflectionBasedAutoSerializer {
        return ReflectionBasedAutoSerializer(".*")
    }
    @Bean("bankAccountRegion")
    fun bankAccountRegion(gemFireCache: GemFireCache) : ClientRegionFactoryBean<String,BankAccount>
    {
        var bankAccountRegion = ClientRegionFactoryBean<String, BankAccount>()
        bankAccountRegion.cache = gemFireCache
        bankAccountRegion.setRegionName("BankAccount")
        bankAccountRegion.setDataPolicy(DataPolicy.EMPTY)
        return bankAccountRegion;
    }

    @Bean
    fun gemfireTemple(gemFireCache: GemFireCache, region: ClientRegionFactoryBean<String,BankAccount>) : GemfireTemplate{
        return GemfireTemplate(region.region);
    }
}