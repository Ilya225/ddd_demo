package arch.example.trader.layer.entity

import java.math.BigDecimal
import java.time.Instant
import java.util.UUID

data class Order(
    val id: UUID,
    val traderId: UUID,
    val assetId: UUID,
    val price: BigDecimal,
    val type: OrderType,
    val quantity: Long,
    val placedAt: Instant
)

enum class OrderType {
    BUY, SELL
}
