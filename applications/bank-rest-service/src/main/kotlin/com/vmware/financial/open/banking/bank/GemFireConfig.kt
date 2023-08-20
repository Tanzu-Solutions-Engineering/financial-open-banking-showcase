package com.vmware.financial.open.banking.bank

import com.vmware.financial.open.banking.account.repository.AccountRepository
import org.springframework.context.annotation.Configuration
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication
import org.springframework.data.gemfire.config.annotation.EnableClusterDefinedRegions
import org.springframework.data.gemfire.config.annotation.EnableContinuousQueries
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories
import org.springframework.data.gemfire.transaction.config.EnableGemfireCacheTransactions

@ClientCacheApplication(subscriptionEnabled = true)
@EnableClusterDefinedRegions
@EnableContinuousQueries
@EnableGemfireRepositories(basePackageClasses = [AccountRepository::class])
@Configuration
@EnableGemfireCacheTransactions
class GemFireConfig {
}