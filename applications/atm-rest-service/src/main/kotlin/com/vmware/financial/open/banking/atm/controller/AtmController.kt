package com.vmware.financial.open.banking.atm.controller

import com.vmware.financial.open.banking.atm.domain.Atm
import com.vmware.financial.open.banking.atm.service.AtmService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * @author Gregory Green
 */
@RestController("/obp/v4.0.0/")
class AtmController(private val atmService: AtmService) {
    @PostMapping("banks/{bankId}/atms")
    fun createAtm(@PathVariable bankId : String, @RequestBody atm : Atm) : ResponseEntity<Atm>
    {
        return ResponseEntity.ok(atmService.createAtm(bankId,atm))
    }

    @GetMapping("banks/{bankId}/atms/{atmId}")
    fun getAtm(bankId: String, atmId: String): ResponseEntity<Atm> {
        var atm = atmService.getAtm(bankId,atmId)
        if(atm.isEmpty)
            return ResponseEntity.notFound().build()

        return ResponseEntity.ok(atm.get());

    }
}