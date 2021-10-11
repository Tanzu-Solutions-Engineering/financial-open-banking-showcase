package com.vmware.financial.open.banking.account.domain

data class Account(var id : String = "",
                   var bank_id: String = "",
                   var label : String = "",
                   var number : String = "",
                   var product_code : String = "",
                   var balance : Balance = Balance(),
                   var account_routings : Array<AccountRouting>? = null,
                   var views_basic : Array<String?>? = null,
                   var key : String  =""
) {
    constructor(
        id: String,
        bank_id: String,
        label: String,
        number: String,
        product_code: String,
        balance: Balance,
        account_routings: Array<String>,
        views_basic: Array<String?>?,
        key: String
    ) : this(id = id, bank_id = bank_id, label = label, number = number,product_code=product_code,balance=balance, account_routings = arrayOf(AccountRouting(account_routings)),
        views_basic = views_basic, key = key)

    constructor(id: String, bank_id: String, label: String, number: String, product_code: String, balance: Balance, account_routings: Array<String>, views_basic: String?, key: String)
            : this(id = id, bank_id = bank_id, label = label, number = number, product_code = product_code, balance = balance, account_routings = arrayOf(AccountRouting(account_routings)) , views_basic = arrayOf(views_basic), key = key)

    constructor(id: String, bank_id: String, label: String, number: String, product_code: String, balance: Balance, account_routings: String, views_basic: String?, key: String)
            : this(id = id, bank_id = bank_id, label = label, number = number, product_code = product_code, balance = balance, account_routings = arrayOf(AccountRouting(account_routings)) , views_basic = arrayOf(views_basic), key = key)


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Account

        if (id != other.id) return false
        if (bank_id != other.bank_id) return false
        if (label != other.label) return false
        if (number != other.number) return false
        if (product_code != other.product_code) return false
        if (balance != other.balance) return false
        if (!account_routings.contentEquals(other.account_routings)) return false
        if (!views_basic.contentEquals(other.views_basic)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + bank_id.hashCode()
        result = 31 * result + label.hashCode()
        result = 31 * result + number.hashCode()
        result = 31 * result + product_code.hashCode()
        result = 31 * result + balance.hashCode()
        result = 31 * result + account_routings.contentHashCode()
        result = 31 * result + views_basic.contentHashCode()
        return result
    }
}
