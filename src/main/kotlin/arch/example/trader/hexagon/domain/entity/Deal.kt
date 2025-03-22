package arch.example.trader.hexagon.domain.entity

import arch.example.trader.component.ddd.vo.Money
import java.time.Instant
import java.util.UUID

@JvmInline
value class DealId(val id: UUID)

data class Deal(
    val dealId: DealId,
    val sellOrderId: OrderId,
    val sellerId: UserId,
    val buyOrderId: OrderId,
    val buyerId: UserId,
    val assetId: AssetId,
    val quantity: Long,
    val price: Money,
    val unitPrice: Money,
    val createdAt: Instant,
)