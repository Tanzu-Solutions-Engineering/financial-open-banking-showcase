package com.vmware.financial.open.banking.bank.sink

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AtmGeodeSinkApp

fun main(args: Array<String>) {
	runApplication<AtmGeodeSinkApp>(*args)
}