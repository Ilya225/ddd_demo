package arch.example.trader.cqrs.command.domain.entity

import arch.example.trader.component.ddd.vo.Money
import java.time.Instant
import java.util.UUID

@JvmInline
value class DealId(val id: UUID)

data class Deal(
    val id: DealId,
    val sellOrderId: OrderId,
    val buyOrderId: OrderId,
    val assetId: AssetId,
    val quantity: Long,
    val price: Money,
    val createdAt: Instant,
)