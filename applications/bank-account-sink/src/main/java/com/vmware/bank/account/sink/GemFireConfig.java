package com.vmware.bank.account.sink;

import com.vmware.financial.open.banking.account.service.AccountService;
import com.vmware.financial.open.banking.domain.account.BankAccount;
import com.vmware.financial.open.banking.gemfire.account.event.service.AccountDataService;
import org.apache.geode.cache.DataPolicy;
import org.apache.geode.cache.GemFireCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.gemfire.GemfireTemplate;
import org.springframework.data.gemfire.client.ClientRegionFactoryBean;
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;


@Configuration
@Profile("gemfire")
@ClientCacheApplication
@EnableGemfireRepositories
public class GemFireConfig {
    public GemFireConfig()
    {
        System.out.printf("created");
    }

    //create region --name=BankAccount --type=PARTITION
    @Bean
    ClientRegionFactoryBean<String, BankAccount> bankAccount(GemFireCache gemFireCache)
    {
        var factory = new ClientRegionFactoryBean<String, BankAccount>();
        factory.setCache(gemFireCache);
        factory.setName("BankAccount");
        factory.setDataPolicy(DataPolicy.EMPTY);
        return factory;
    }

    @Bean
    AccountService dataService(GemFireCache gemFireCache, GemfireTemplate gemfireTemplate)
    {
        return new AccountDataService(gemfireTemplate);
    }
}
