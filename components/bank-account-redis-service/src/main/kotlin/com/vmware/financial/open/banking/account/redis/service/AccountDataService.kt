package com.vmware.financial.open.banking.account.redis.service

import com.vmware.financial.open.banking.account.service.AccountService
import com.vmware.financial.open.banking.domain.account.BankAccount
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.util.*

/**
 * @author Gregory Green
 */
@Service
class AccountDataService(private val template: RedisTemplate<String,BankAccount>) : AccountService {

    private val opsForValue = template.opsForValue()

    override fun createAccount(account: BankAccount): BankAccount {
        opsForValue[toKey(account)] = account
        return account
    }

    /**
     * Fingd account by identifies
     * @param bankId the bank ind
     * @param accountId the account id
     * @return Optional account
     */
    override fun findAccountById(bankId: String, accountId: String): Optional<BankAccount> {

        var accountObj  = opsForValue[toKey(bankId,accountId)]
            ?: return Optional.empty()

        return Optional.of(accountObj)
    }

    override fun updateAccount(account: BankAccount): Optional<BankAccount> {
        var key = toKey(account)
        if(!opsForValue.setIfAbsent(key,account)!!)
            return Optional.empty()

        return Optional.of(account)
    }

    override fun saveAccount(account: BankAccount): BankAccount {
        opsForValue[toKey(account)] = account
        return account
    }
}