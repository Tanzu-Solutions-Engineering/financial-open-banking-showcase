package showcase.financial.open.banking;

import com.vmware.financial.open.banking.account.repository.AccountRepository;
import com.vmware.financial.open.banking.account.service.AccountService;
import com.vmware.financial.open.banking.domain.account.BankAccount;
import com.vmware.financial.open.banking.gemfire.account.event.service.AccountDataService;
import org.apache.geode.cache.DataPolicy;
import org.apache.geode.cache.GemFireCache;
import org.apache.geode.pdx.ReflectionBasedAutoSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.gemfire.GemfireTemplate;
import org.springframework.data.gemfire.client.ClientRegionFactoryBean;
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication;
import org.springframework.data.gemfire.config.annotation.EnableContinuousQueries;
import org.springframework.data.gemfire.config.annotation.EnablePdx;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;

@Profile("gemfire")
@Configuration
@EnableContinuousQueries
@EnableGemfireRepositories(basePackageClasses = {AccountRepository.class})
@ClientCacheApplication(subscriptionEnabled = true)
@EnablePdx
public class GemFireConfig {

    @Bean
    ReflectionBasedAutoSerializer serializer()   {
        return new ReflectionBasedAutoSerializer(".*");
    }
    @Bean("bankAccountRegion")
    ClientRegionFactoryBean<String, BankAccount> bankAccountRegion(GemFireCache gemFireCache)
    {
        var bankAccountRegion = new ClientRegionFactoryBean<String, BankAccount>();
        bankAccountRegion.setCache(gemFireCache);
        bankAccountRegion.setRegionName("BankAccount");
        bankAccountRegion.setDataPolicy(DataPolicy.EMPTY);
        return bankAccountRegion;
    }
    @Bean
    AccountService accountService(GemFireCache gemFireCache, GemfireTemplate gemfireTemplate) {
        return new AccountDataService(gemfireTemplate);
    }

    @Bean
    GemfireTemplate gemfireTemple(GemFireCache gemFireCache, ClientRegionFactoryBean<String,BankAccount> region) {
        return new GemfireTemplate(region.getRegion());
    }
}
