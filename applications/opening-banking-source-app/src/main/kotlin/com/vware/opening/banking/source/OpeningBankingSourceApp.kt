package com.vware.opening.banking.source

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class OpeningBankingSourceApp

fun main(args: Array<String>) {
	runApplication<OpeningBankingSourceApp>(*args)
}
