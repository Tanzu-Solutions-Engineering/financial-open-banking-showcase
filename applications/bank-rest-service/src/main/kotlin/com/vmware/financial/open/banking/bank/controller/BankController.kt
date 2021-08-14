package com.vmware.financial.open.banking.bank.controller

import com.vmware.financial.open.banking.bank.domain.Bank
import com.vmware.financial.open.banking.bank.service.BankService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

/**
 * @author Gregory Green
 */
@RestController("/obp/v4.0.0/")
class BankController(private val bankService: BankService) {
    @PostMapping("banks")
    fun createBank(@RequestBody bank: Bank): ResponseEntity<Bank> {
        return ResponseEntity.ok(bankService.createBank(bank))
    }
    @GetMapping("banks/{bankId}")
    fun getBank(@PathVariable bankId: String): ResponseEntity<Bank> {
        var bank = bankService.getBank(bankId);
        if(bank.isEmpty)
            return ResponseEntity.notFound().build()

        return ResponseEntity.ok(bank.get())
    }


}