package com.vmware.financial.open.banking.atm

import com.vmware.financial.open.banking.atm.domain.Atm
import org.apache.geode.cache.DataPolicy
import org.apache.geode.cache.GemFireCache
import org.apache.geode.pdx.ReflectionBasedAutoSerializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.gemfire.GemfireTemplate
import org.springframework.data.gemfire.client.ClientRegionFactoryBean
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication
import org.springframework.data.gemfire.config.annotation.EnablePdx

/**
 * @author Gregory Green
 */
@ClientCacheApplication
@Configuration
@EnablePdx
class GemFireConf {

    @Bean
    fun serializer() : ReflectionBasedAutoSerializer {
        return ReflectionBasedAutoSerializer(".*")
    }

    @Bean
    fun atmRegion(gemFireCache: GemFireCache) : ClientRegionFactoryBean<String,Atm>
    {
        var region = ClientRegionFactoryBean<String,Atm>()
        region.cache = gemFireCache
        region.setDataPolicy(DataPolicy.EMPTY)
        region.setRegionName("Atm")
        return region

    }

    @Bean
    fun atmGemFireTemplate(region: ClientRegionFactoryBean<String,Atm>) : GemfireTemplate
    {
        return GemfireTemplate(region.region);
    }
}