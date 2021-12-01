package com.vmware.financial.open.banking.account.service

import com.vmware.financial.open.banking.account.domain.BankAccount
import com.vmware.financial.open.banking.account.domain.BankAccountCreateDto
import nyla.solutions.core.util.Text
import org.apache.geode.pdx.PdxInstance
import org.springframework.data.gemfire.GemfireTemplate
import org.springframework.stereotype.Service
import java.util.*

/**
 * @author Gregory Green
 */
@Service
class AccountDataService(private val gemFireTemplate: GemfireTemplate) : AccountService  {
    fun toKey(bankId: String, accountId: String): String {
        return "$bankId|$accountId"
    }

    /**
     * Convert account to id
     */
     fun toKey(account: BankAccount): String
     {
         return toKey(account.bank_id,account.id)
     }

    /**
     * Create an account
     * @param accountDto the account data transfer object
     * @return the BankAccountCreate Data transfer object
     */
    override fun createAccount(bankId : String, accountDto: BankAccountCreateDto): BankAccountCreateDto {
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
    internal fun toAccount(bankId: String, accountDto: BankAccountCreateDto): BankAccount {
        return BankAccount(
            id = accountDto.account_id,
            label = accountDto.label,
            product_code = accountDto.product_code,
            balance = accountDto.balance,
            account_routings = accountDto.account_routings,
            bank_id = bankId

        )
    }

    override fun createAccount(account: BankAccount): BankAccount {
        gemFireTemplate.put(toKey(account),account)
        return account
    }

    /**
     * Fingd account by identifies
     * @param bankId the bank ind
     * @param accountId the account id
     * @return Optional account
     */
    override fun findAccountById(bankId: String, accountId: String): Optional<BankAccount> {
        var accountObj : Any = gemFireTemplate[toKey(bankId,accountId)] ?: return Optional.empty()

        if(accountObj is PdxInstance)
        {
            var account : BankAccount? = accountObj.`object` as BankAccount?

            return Optional.of(account!!)
        }

        var account : BankAccount? = accountObj as BankAccount?
        return Optional.of(account!!)
    }

    override fun updateAccount(account: BankAccount): Optional<BankAccount> {
        var key = toKey(account)
        if(!gemFireTemplate.containsKeyOnServer(key))
            return Optional.empty()

        gemFireTemplate.put(key,account)
        return Optional.of(account)
    }
}