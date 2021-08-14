package com.vmware.financial.open.banking.account

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication
import org.springframework.data.gemfire.config.annotation.EnableClusterDefinedRegions

@SpringBootApplication
class AccountOpenBankingApp

fun main(args: Array<String>) {
	runApplication<AccountOpenBankingApp>(*args)
}
