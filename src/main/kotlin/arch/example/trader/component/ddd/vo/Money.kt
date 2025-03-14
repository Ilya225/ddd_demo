package arch.example.trader.component.ddd.vo

import java.math.BigDecimal
import java.util.Currency

data class Money(
    val amount: BigDecimal,
    val currency: Currency,
) : Comparable<Money> {
    operator fun plus(other: Money): Money {
        require(this.currency == other.currency) { "Currencies must match" }
        return Money(this.amount + other.amount, this.currency)
    }

    operator fun minus(other: Money): Money {
        require(this.currency == other.currency) { "Currencies must match" }
        return Money(this.amount - other.amount, this.currency)
    }

    operator fun times(multiplier: Long): Money =
        Money(this.amount.multiply(BigDecimal(multiplier)), this.currency)

    override fun compareTo(other: Money): Int {
        return this.amount.compareTo(other.amount)
    }

    override fun toString(): String = "$amount $currency"

    companion object {
        fun of(amount: BigDecimal, currency: Currency) = Money(amount, currency)
    }
}
