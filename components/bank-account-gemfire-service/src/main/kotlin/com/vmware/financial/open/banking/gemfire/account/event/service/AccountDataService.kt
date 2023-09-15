package com.vmware.financial.open.banking.gemfire.account.event.service

import com.vmware.financial.open.banking.account.service.AccountService
import com.vmware.financial.open.banking.domain.account.BankAccount
import com.vmware.financial.open.banking.domain.account.BankAccountCreateDto
import nyla.solutions.core.util.Text
import org.apache.geode.pdx.PdxInstance
import org.springframework.context.annotation.Profile
import org.springframework.data.gemfire.GemfireTemplate
import org.springframework.stereotype.Service
import java.util.*

/**
 * @author Gregory Green
 */
@Profile("gemfire")
@Service
class AccountDataService(private val gemFireTemplate: GemfireTemplate) : AccountService {

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

    override fun saveAccount(account: BankAccount): BankAccount {
        gemFireTemplate.put(toKey(account),account)
        return account
    }
}