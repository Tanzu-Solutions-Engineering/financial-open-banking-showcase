package com.vmware.financial.open.banking.account.service

import com.vmware.financial.open.banking.domain.account.BankAccount
import com.vmware.financial.open.banking.domain.account.BankAccountCreateDto
import nyla.solutions.core.util.Text
import java.util.*

interface AccountService {

    /**
     * Convert account to id
     */
    fun toKey(account: BankAccount): String
    {
        return toKey(account.bank_id,account.id)
    }

    fun toKey(bankId: String, accountId: String): String {
        return "$bankId|$accountId"
    }

    /**
     * Create an account
     * @param accountDto the account data transfer object
     * @return the BankAccountCreate Data transfer object
     */
    fun createAccount(bankId : String, accountDto: BankAccountCreateDto): BankAccountCreateDto {
        if(accountDto.account_id.isEmpty())
        {
            accountDto.account_id = Text.generateId()
        }

        this.createAccount(toAccount(bankId, accountDto))

        return accountDto
    }


    /**
     * Convert DTO to account
     * @param accountDto the account data transfer object
     * @return the converted account
     */
    fun toAccount(bankId: String, accountDto: BankAccountCreateDto): BankAccount {
        return BankAccount(
            id = accountDto.account_id,
            label = accountDto.label,
            product_code = accountDto.product_code,
            balance = accountDto.balance,
            account_routings = accountDto.account_routings,
            bank_id = bankId

        )
    }

    fun createAccount(account: BankAccount) : BankAccount
    fun findAccountById(bankId: String, accountId: String): Optional<BankAccount>
    fun updateAccount(account: BankAccount): Optional<BankAccount>

}