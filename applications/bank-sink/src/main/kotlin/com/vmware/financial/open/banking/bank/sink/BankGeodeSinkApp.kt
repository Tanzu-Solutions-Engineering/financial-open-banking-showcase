package com.vmware.financial.open.banking.bank.sink

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BankOpenBankingApp

fun main(args: Array<String>) {
	runApplication<BankOpenBankingApp>(*args)
}