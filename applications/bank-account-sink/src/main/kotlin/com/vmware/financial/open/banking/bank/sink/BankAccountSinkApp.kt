package com.vmware.financial.open.banking.bank.sink

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BankAccountSinkApp

fun main(args: Array<String>) {
	runApplication<BankAccountSinkApp>(*args)
}