package com.vmware.financial.open.banking.atm;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication;
import org.springframework.data.gemfire.config.annotation.EnableClusterDefinedRegions;
import org.springframework.data.gemfire.transaction.config.EnableGemfireCacheTransactions;

@ClientCacheApplication
@EnableClusterDefinedRegions
@Configuration
public class GeodeConf
{
}
