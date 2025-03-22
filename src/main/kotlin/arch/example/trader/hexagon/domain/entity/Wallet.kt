package arch.example.trader.hexagon.domain.entity

import arch.example.trader.component.ddd.vo.Money
import java.time.Instant
import java.util.*


@JvmInline
value class WalletId(val id: UUID)

data class Wallet(
    val id: WalletId,
    val userId: UserId,
    val balance: Money,
    val createdAt: Instant,
    val updatedAt: Instant
) {

    fun deposit(amount: Money): Wallet =
        this.copy(balance = balance + amount, updatedAt = Instant.now())

    fun withDraw(amount: Money): Wallet {
        if (amount > balance) {
            throw IllegalArgumentException("Can't withdraw more money than on the balance")
        }
        return this.copy(balance = balance - amount, updatedAt = Instant.now())
    }

    fun enoughFunds(amount: Money, quantity: Long) =
        this.balance >= (amount * quantity)


}