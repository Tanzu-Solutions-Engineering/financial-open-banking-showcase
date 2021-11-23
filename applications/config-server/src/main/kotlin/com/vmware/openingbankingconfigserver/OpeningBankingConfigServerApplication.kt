package com.vmware.openingbankingconfigserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.config.server.EnableConfigServer

@SpringBootApplication
@EnableConfigServer
class OpeningBankingConfigServerApplication

fun main(args: Array<String>) {
	runApplication<OpeningBankingConfigServerApplication>(*args)
}
