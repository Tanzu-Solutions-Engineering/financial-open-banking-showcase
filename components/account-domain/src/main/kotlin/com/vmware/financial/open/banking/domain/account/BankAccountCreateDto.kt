package com.vmware.financial.open.banking.domain.account

data class BankAccountCreateDto(
    var account_id : String = "",
    var user_id : String = "",
    var label : String = "",
    var product_code : String = "",
    var balance : Balance = Balance(),
    var account_routings : Array<AccountRouting>? = null,
    var branch_id  : String = ""
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BankAccountCreateDto

        if (user_id != other.user_id) return false
        if (label != other.label) return false
        if (product_code != other.product_code) return false
        if (balance != other.balance) return false
        if (account_routings != null) {
            if (other.account_routings == null) return false
            if (!account_routings.contentEquals(other.account_routings)) return false
        } else if (other.account_routings != null) return false
        if (branch_id != other.branch_id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = user_id.hashCode()
        result = 31 * result + label.hashCode()
        result = 31 * result + product_code.hashCode()
        result = 31 * result + balance.hashCode()
        result = 31 * result + (account_routings?.contentHashCode() ?: 0)
        result = 31 * result + branch_id.hashCode()
        return result
    }
}
