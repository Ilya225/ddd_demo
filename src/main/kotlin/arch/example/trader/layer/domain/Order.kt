package arch.example.trader.layer.domain

import java.math.BigDecimal
import java.time.Instant
import java.util.UUID

data class Order(
    val id: UUID,
    val traderId: UUID,
    val assetId: UUID,
    val unitPrice: BigDecimal,
    val type: OrderType,
    val quantity: Long,
    val placedAt: Instant
)

enum class OrderType {
    BUY, SELL
}
