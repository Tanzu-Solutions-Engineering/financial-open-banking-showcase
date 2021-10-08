package com.vmware.financial.open.banking.account.service

import com.vmware.financial.open.banking.account.domain.Account
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
     fun toKey(account: Account): String
     {
         return toKey(account.bank_id,account.id)
     }

    override fun createAccount(account: Account): Account {
        gemFireTemplate.put(toKey(account),account)
        return account
    }

    /**
     * Fingd account by identifies
     * @param bankId the bank ind
     * @param accountId the account id
     * @return Optional account
     */
    override fun findAccountById(bankId: String, accountId: String): Optional<Account> {
        var accountObj : Any = gemFireTemplate[toKey(bankId,accountId)] ?: return Optional.empty()

        if(accountObj is PdxInstance)
        {
            var account : Account? = accountObj.`object` as Account?

            return Optional.of(account!!)
        }

        var account : Account? = accountObj as Account?
        return Optional.of(account!!)
    }

    override fun updateAccount(account: Account): Optional<Account> {
        var key = toKey(account)
        if(!gemFireTemplate.containsKeyOnServer(key))
            return Optional.empty()

        gemFireTemplate.put(key,account)
        return Optional.of(account)
    }
}