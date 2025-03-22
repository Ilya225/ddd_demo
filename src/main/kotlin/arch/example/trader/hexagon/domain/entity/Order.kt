package arch.example.trader.hexagon.domain.entity

import arch.example.trader.component.ddd.vo.Money
import java.math.BigDecimal
import java.time.Instant
import java.util.*

@JvmInline
value class OrderId(val id: UUID)

data class Order(
    val id: OrderId,
    val traderId: UserId,
    val assetId: AssetId,
    val type: OrderType,
    val unitPrice: Money,
    val quantity: Long,
    val placedAt: Instant
) {

    val price: Money = unitPrice.times(quantity)

    fun isMatch(match: Order): Boolean {
        return if (this.type == OrderType.BUY) this.price >= match.price
        else this.price <= match.price
    }

    fun allocate(ratio: BigDecimal = BigDecimal(1.0)): Order {
        return this.copy(quantity = ratio.multiply(BigDecimal(quantity)).toLong())
    }

    fun allocate(quantity: Long): Order {
        return this.copy(quantity = this.quantity - quantity)
    }
}

enum class OrderType {
    SELL, BUY
}
