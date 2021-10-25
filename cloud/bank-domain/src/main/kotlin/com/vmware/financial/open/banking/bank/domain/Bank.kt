package com.vmware.financial.open.banking.bank.domain

data class Bank(
    var id : String = "",
    var short_name : String = "",
    var full_name : String = "",
    var logo : String = "",
    var website : String = "",
    var bank_routings : Array<BankRoute>? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Bank

        if (id != other.id) return false
        if (short_name != other.short_name) return false
        if (full_name != other.full_name) return false
        if (logo != other.logo) return false
        if (website != other.website) return false
        if (bank_routings != null) {
            if (other.bank_routings == null) return false
            if (!bank_routings.contentEquals(other.bank_routings)) return false
        } else if (other.bank_routings != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + short_name.hashCode()
        result = 31 * result + full_name.hashCode()
        result = 31 * result + logo.hashCode()
        result = 31 * result + website.hashCode()
        result = 31 * result + (bank_routings?.contentHashCode() ?: 0)
        return result
    }
}
