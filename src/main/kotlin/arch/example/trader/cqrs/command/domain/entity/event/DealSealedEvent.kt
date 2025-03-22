package arch.example.trader.cqrs.command.domain.entity.event

import arch.example.trader.component.ddd.vo.Money
import arch.example.trader.cqrs.command.domain.entity.AssetId
import arch.example.trader.cqrs.command.domain.entity.DealId
import arch.example.trader.cqrs.command.domain.entity.MatcherType
import arch.example.trader.cqrs.command.domain.entity.OrderId
import arch.example.trader.cqrs.command.domain.entity.UserId

data class DealSealedEvent(
    val dealId: DealId,
    val sellOrderId: OrderId,
    val buyOrderId: OrderId,
    val buyerId: UserId,
    val sellerId: UserId,
    val assetId: AssetId,
    val quantity: Long,
    val unitPrice: Money,
    val matchedBy: MatcherType
)