package com.vmware.financial.open.banking.account.domain

data class Account(var id : String = "",
                   var bank_id: String = "",
                   var label : String = "",
                   var number : String = "",
                   var product_code : String = "",
                   var balance : Balance = Balance(),
                   var account_routings : Array<AccountRouting>? = null,
                   var views_basic : Array<String>? = null,
                   var key : String  =""
) {
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
