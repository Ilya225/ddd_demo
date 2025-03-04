package arch.example.trader.hexagon.domain.entity

import java.math.BigDecimal
import java.util.UUID

@JvmInline
value class OrderId(val id: UUID)

data class Order(
    val assetId: AssetId,
    val type: OrderType,
    val maxPriceCap: BigDecimal,
    val minPriceCap: BigDecimal
)

enum class OrderType {
    SELL, BUY, CANCEL
}



data class OrderBatch(
    val id: OrderId,
    val accountId: UserId,
    val items: List<Order>
)




