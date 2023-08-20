package com.vmware.financial.open.banking.bank.sink

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.vmware.financial.open.banking.atm.domain.Atm
import com.vmware.financial.open.banking.atm.service.AtmService
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

/**
 * BankConsumerTest test for AtmConsumer
 */
internal class AtmConsumerTest {

    @Test
    fun accept() {
        var atmService : AtmService = mock<AtmService>()
        var subject  = AtmConsumer(atmService)
        val atm = JavaBeanGeneratorCreator.of(Atm::class.java).create()
        subject.accept(atm)
        verify(atmService).createAtm(atm.bank_id,atm)
    }

    @Test
    internal fun parsing() {

        var mapper = jacksonObjectMapper()
            .registerModule(KotlinModule.Builder().build())


        //var json = "{\"saturday\":\"7:00AM-9:00PM-7:00AM-9:00PM\",\"address\":\"124 Straight Street,Jersey City,NJ,07305,US\",\"update_ts\":\"2022-04-07T19:01:38.746+00:00\",\"located_at\":\"truth street\",\"thursday\":\"7:00AM-9:00PM-7:00AM-9:00PM\",\"more_info\":\"more info\",\"is_accessible\":\"true\",\"has_deposit_capability\":\"true\",\"sunday\":\"7:00AM-9:00PM-7:00AM-9:00PM\",\"tuesday\":\"7:00AM-9:00PM-7:00AM-9:00PM\",\"bank_id\":\"bank1\",\"name\":\"atm1\",\"wednesday\":\"7:00AM-9:00PM-7:00AM-9:00PM\",\"friday\":\"7:00AM-9:00PM-7:00AM-9:00PM\",\"atm_id\":\"atm1\",\"id\":\"atm1\",\"monday\":\"7:00AM-9:00PM-7:00AM-9:00PM\"}"
        var json = "{\"saturday\":\"{\\\"opening_time\\\":\\\"7:00AM-9:00PM\\\", \\\"closing_time\\\":\\\"7:00AM-9:00PM\\\"}\",\"address\":\"{\\\"line_1\\\":\\\"224 Straight Street\\\",\\\"line_2\\\": \\\"\\\", \\\"line_3\\\": \\\"\\\", \\\"city\\\": \\\"Jersey City\\\", \\\"county\\\": \\\"\\\", \\\"state\\\": \\\"NJ\\\", \\\"postcode\\\": \\\"07305\\\", \\\"country_code\\\": \\\"US\\\"\",\"update_ts\":\"2022-08-15T19:57:35.435+00:00\",\"located_at\":\"truth street\",\"thursday\":\"{\\\"opening_time\\\":\\\"7:00AM-9:00PM\\\", \\\"closing_time\\\":\\\"7:00AM-9:00PM\\\"}\",\"more_info\":\"more info\",\"is_accessible\":\"true\",\"has_deposit_capability\":\"true\",\"sunday\":\"{\\\"opening_time\\\":\\\"7:00AM-9:00PM\\\", \\\"closing_time\\\":\\\"7:00AM-9:00PM\\\"}\",\"tuesday\":\"{\\\"opening_time\\\":\\\"7:00AM-9:00PM\\\", \\\"closing_time\\\":\\\"7:00AM-9:00PM\\\"}\",\"bank_id\":\"bank1\",\"name\":\"atm2\",\"wednesday\":\"{\\\"opening_time\\\":\\\"7:00AM-9:00PM\\\", \\\"closing_time\\\":\\\"7:00AM-9:00PM\\\"}\",\"friday\":\"{\\\"opening_time\\\":\\\"7:00AM-9:00PM\\\", \\\"closing_time\\\":\\\"7:00AM-9:00PM\\\"}\",\"atm_id\":\"atm2\",\"id\":\"atm2\",\"monday\":\"{\\\"opening_time\\\":\\\"7:00AM-9:00PM\\\", \\\"closing_time\\\":\\\"7:00AM-9:00PM\\\"}\"}"

        var atm = JavaBeanGeneratorCreator.of(Atm::class.java).create()
        var out = mapper.writeValueAsString(atm)

        println(out)
    }

    @Test
    internal fun parsingJackson() {

        var mapper = ObjectMapper()

        var atmObject = JavaBeanGeneratorCreator.of(Atm::class.java).create()

        var json = mapper.writeValueAsString(atmObject)

        println("json: $json")


        var atm = JavaBeanGeneratorCreator.of(Atm::class.java).create()
        var out = mapper.writeValueAsString(atm)

        println(out)



    }
}