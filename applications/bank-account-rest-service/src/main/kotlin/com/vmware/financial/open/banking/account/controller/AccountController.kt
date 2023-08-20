package com.vmware.financial.open.banking.account.controller

import com.vmware.financial.open.banking.account.service.AccountService
import com.vmware.financial.open.banking.domain.account.BankAccount
import com.vmware.financial.open.banking.domain.account.BankAccountCreateDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * @author Gregory Green
 */
@RestController
@RequestMapping("/obp/v4.0.0/")
class AccountController(
    private val accountService: AccountService
) {

    @PostMapping("banks/{bankId}/accounts")
    fun createAccount(@PathVariable("bankId") bankId : String, @RequestBody account: BankAccountCreateDto) : ResponseEntity<BankAccountCreateDto> {
        return ResponseEntity.ok(accountService.createAccount(bankId,account))
    }


    @GetMapping("banks/{bankId}/accounts/{accountId}/account")
    fun getAccountById(@PathVariable("bankId")bankId: String,@PathVariable("accountId") accountId: String): ResponseEntity<BankAccount> {
        var account = accountService.findAccountById(bankId,accountId)
        if(account.isPresent)
            return ResponseEntity.ok(account.get())

        return ResponseEntity.notFound().build()
    }

}