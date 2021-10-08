package com.vmware.financial.open.banking.account.controller

import com.vmware.financial.open.banking.account.domain.Account
import com.vmware.financial.open.banking.account.service.AccountService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.websocket.server.PathParam

/**
 * @author Gregory Green
 */
@RestController("/obp/v4.0.0")
class AccountController(
    private val accountService: AccountService
) {

    @PostMapping("banks/{bankId}/accounts")
    fun createAccount(@PathVariable("bankId") bankId : String, @RequestBody account: Account) : ResponseEntity<Account> {
        return ResponseEntity.ok(accountService.createAccount(account))
    }

    @PutMapping("banks/{bankId}/accounts")
    fun updateAccount(@PathVariable("bankId") bankId : String, @RequestBody account: Account) : ResponseEntity<Account> {
        var account = accountService.updateAccount(account);

        if(account.isEmpty)
            return ResponseEntity.notFound().build()

        return ResponseEntity.ok(account.get())

    }


    @GetMapping("banks/{bankId}/accounts/{accountId}")
    fun getAccountById(@PathVariable("bankId")bankId: String,@PathVariable("accountId") accountId: String): ResponseEntity<Account> {
        var account = accountService.findAccountById(bankId,accountId)
        if(account.isPresent)
            return ResponseEntity.ok(account.get())

        return ResponseEntity.notFound().build()
    }

}