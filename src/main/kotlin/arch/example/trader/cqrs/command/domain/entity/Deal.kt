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
    val buyerId: UserId,
    val sellerId: UserId,
    val assetId: AssetId,
    val quantity: Long,
    val unitPrice: Money,
    val createdAt: Instant,
    val matchedBy: MatcherType
)

enum class MatcherType {
    PRICE_TIME, MARKET_MAKER, PRO_RATA
}